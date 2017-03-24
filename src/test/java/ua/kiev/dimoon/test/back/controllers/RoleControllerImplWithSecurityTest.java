package ua.kiev.dimoon.test.back.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.AopTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.kiev.dimoon.test.back.BaseTestClass;
import ua.kiev.dimoon.test.back.dao.model.User;
import ua.kiev.dimoon.test.back.filters.JwtAuthenticationFilter;
import ua.kiev.dimoon.test.back.filters.TokenExtractFromHeaderStrategy;
import ua.kiev.dimoon.test.back.services.TokenServiceImpl;
import ua.kiev.dimoon.test.back.services.interfaces.TokenService;
import ua.kiev.dimoon.test.back.services.interfaces.UserService;

import javax.servlet.Filter;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.kiev.dimoon.test.back.TestObjectFactory.*;

public class RoleControllerImplWithSecurityTest extends BaseTestClass {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private Filter springSecurityFilterChain;
    @Autowired
    private TokenService tokenService;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder =
                MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .addFilters(springSecurityFilterChain);
        this.mockMvc = builder.build();
    }

    @Test
    public void testGetAllRoles() throws Exception {
        mockMvc.perform(get("/roles")
                .header(TokenExtractFromHeaderStrategy.TOKEN_NAME, getRealToken()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private String getRealToken() {
        TokenServiceImpl tokenServiceImpl = AopTestUtils.getTargetObject(tokenService);
        UserService userServiceMock = Mockito.mock(UserService.class);
        tokenServiceImpl.setUserService(userServiceMock);
        User user = createUser(1L, TEST_USER_NAME);
        when(userServiceMock.findUserByUsername(TEST_USER_NAME)).thenReturn(Optional.of(user));
        String token = tokenService.createToken(TEST_USER_NAME, TEST_USER_PASS).orElse(null);
        return token;
    }

}
