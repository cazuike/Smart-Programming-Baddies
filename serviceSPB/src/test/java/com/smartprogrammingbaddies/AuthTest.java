package com.smartprogrammingbaddies;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * This class contains unit tests for Course.java.
 *
 * <p> Unit tests involve setting up an environment for testing and conducting the
 * necessary tests to ensure functionality. </p>
 */

@WebMvcTest(AuthController.class)
public class AuthTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SetupDB setupDB;

    @Test
    public void generateKeyTest() throws Exception {
        mockMvc.perform(get("/generateApiKey"))
                .andExpect(status().isOk());
    }

//    @Test
//    public void verifyKeyTest() throws Exception {
//        mockMvc.perform(get("/verifyApiKey")
//                .param("apiKey", "0d8687d7-9c19-4fb9-a0af-927ffb6b0678"))
//                .andExpect(status().isOk());
//    } - broken currently
}
