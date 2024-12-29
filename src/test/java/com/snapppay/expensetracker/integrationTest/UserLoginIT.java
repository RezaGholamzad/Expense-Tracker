package com.snapppay.expensetracker.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapppay.expensetracker.entity.User;
import com.snapppay.expensetracker.exception.FailureResponse;
import com.snapppay.expensetracker.exception.InvalidPasswordOrUsernameException;
import com.snapppay.expensetracker.model.LoginResponse;
import com.snapppay.expensetracker.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserLoginIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        User user = new User();
        user.setUsername("m.gholamzad");
        user.setFirstName("reza");
        user.setLastName("gholamzad");
        user.setPassword(passwordEncoder.encode("123"));
        userRepository.save(user);
    }

    @Test
    public void testLoginSuccessful() throws Exception {
        var response = mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "m.gholamzad")
                        .param("password", "123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();
        var loginResponse = new ObjectMapper().readValue(response.getContentAsString(), LoginResponse.class);

        Assertions.assertEquals("reza", loginResponse.firstName());
        Assertions.assertEquals("gholamzad", loginResponse.lastName());
        Assertions.assertNotNull(loginResponse.token());
    }

    @Test
    public void testLoginUnauthorized() throws Exception {
        var response = mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "m.gholamzad")
                        .param("password", "1"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();
        var failureResponse = new ObjectMapper().readValue(response.getContentAsString(), FailureResponse.class);

        Assertions.assertEquals(InvalidPasswordOrUsernameException.class.getSimpleName(), failureResponse.exceptionName());
        Assertions.assertNotNull(failureResponse.message());
    }
}
