package br.com.karatedopi.controllers;

import br.com.karatedopi.factories.FactoryState;
import br.com.karatedopi.services.StateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class StateControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private StateService stateService;

    @Autowired
    private MockMvc mockMvc;

    HttpHeaders headers;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setOrigin("localhost:8081");
    }

    @Test
    public void testAllStatesShouldReturnListOfStatesDTO() throws Exception {
        String expectedAllStatesDTOs = objectMapper.writeValueAsString(FactoryState.createAllStatesDTOs());
        when(stateService.getAllStates()).thenReturn(FactoryState.createAllStatesDTOs());
        ResultActions result = mockMvc
                .perform(get("/states/all")
                        .headers(headers)
                        .accept(MediaType.APPLICATION_JSON)
                );
        result.andExpect(status().isOk());
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        result.andExpect(content().json(expectedAllStatesDTOs));
    }

    @Test
    public void testInvalidCorsWithForbiddenOrigin() throws Exception {
        headers.setOrigin("forbiddenOrigin");
        ResultActions result = mockMvc
                .perform(get("/states/all")
                        .headers(headers)
                        .accept(MediaType.APPLICATION_JSON)
                );
        result.andExpect(status().isForbidden());
    }

}
