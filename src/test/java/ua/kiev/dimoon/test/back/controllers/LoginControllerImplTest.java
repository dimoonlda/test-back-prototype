package ua.kiev.dimoon.test.back.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.kiev.dimoon.test.back.TestObjectFactory;
import ua.kiev.dimoon.test.back.controllers.interfaces.LoginController;
import ua.kiev.dimoon.test.back.services.interfaces.TokenService;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.kiev.dimoon.test.back.TestObjectFactory.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by admin on 23.03.2017.
 */
public class LoginControllerImplTest {
    @Mock
    private TokenService tokenService;

    @InjectMocks
    private LoginController loginController = new LoginControllerImpl();

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void testGetToken() throws Exception {
        String token = "TOKEN===111";
        when(tokenService.createToken(TEST_USER_NAME, TEST_USER_PASS)).thenReturn(Optional.of(token));

        String requestDto = "{\n" +
                "\t\"userName\": \"" + TEST_USER_NAME + "\",\n" +
                "\t\"passWord\": \"" + TEST_USER_PASS + "\"\n" +
                "}";
        MvcResult mvcResult = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON).content(requestDto))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Mockito.verify(tokenService).createToken(TEST_USER_NAME, TEST_USER_PASS);
        assertThat(token).isEqualTo(mvcResult.getResponse().getContentAsString());
    }
}
