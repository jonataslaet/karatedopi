package br.com.karatedopi.controllers;

import br.com.karatedopi.entities.User;
import br.com.karatedopi.entities.enums.UserStatus;
import br.com.karatedopi.factories.FactoryUser;
import br.com.karatedopi.services.UserService;
import br.com.karatedopi.utils.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    private HttpHeaders headers;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setOrigin("localhost:8081");
    }

    @ParameterizedTest
    @EnumSource(UserStatus.class)
    public void testAllUsersShouldReturnPagedUsersWhenTokenIsCorrect(UserStatus status) throws Exception {
        List<User> users = FactoryUser.createAllUsers();
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> page = new PageImpl<>(users, pageable, users.size());

        when(userService.getPagedUsers(null, status.toString(), pageable)).thenReturn(page);

        String accessToken = tokenUtil.obtainAccessToken(mockMvc, "blendolove@hotmail.com", "blendo273");
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testInvalidCorsWithForbiddenOriginAndCorrectToken() throws Exception {
        String accessToken = tokenUtil.obtainAccessToken(mockMvc, "blendolove@hotmail.com", "blendo273");
        headers.setOrigin("forbiddenOrigin");
        headers.setBearerAuth("Bearer " + accessToken);
        ResultActions result = mockMvc
                .perform(get("/users")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                );
        result.andExpect(status().isForbidden());
    }

}
