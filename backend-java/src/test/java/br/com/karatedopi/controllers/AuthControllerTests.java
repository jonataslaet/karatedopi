package br.com.karatedopi.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private String adminUsername;
    private String adminPassword;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        adminUsername = "blendolove@hotmail.com";
        adminPassword = "blendo273";
    }

    @Test
    public void testLoginSuccessWithCorrectAdminCredentials() throws Exception  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setOrigin("localhost:8081");

        String jsonBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", adminUsername, adminPassword);

        ResultActions result = mockMvc
                .perform(post("/login")
                        .headers(headers)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                );

        result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testLoginFailWithWrongAdminCredentials() throws Exception  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setOrigin("localhost:8081");

        String jsonBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", adminUsername, "wrongpassword");

        ResultActions result = mockMvc
                .perform(post("/login")
                        .headers(headers)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                );

        result.andExpect(status().is4xxClientError()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
