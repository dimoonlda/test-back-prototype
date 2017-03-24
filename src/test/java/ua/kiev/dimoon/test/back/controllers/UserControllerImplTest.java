package ua.kiev.dimoon.test.back.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.kiev.dimoon.test.back.controllers.interfaces.UserController;
import ua.kiev.dimoon.test.back.dao.model.dto.RoleDto;
import ua.kiev.dimoon.test.back.dao.model.dto.UserDto;
import ua.kiev.dimoon.test.back.services.interfaces.UserService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.kiev.dimoon.test.back.TestObjectFactory.*;

/**
 * Created by admin on 24.03.2017.
 */
public class UserControllerImplTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController = new UserControllerImpl();

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        UserDto userDto1 = createUserDto(1L, TEST_USER_NAME);
        UserDto userDto2 = createUserDto(2L, TEST_USER_NAME);
        when(userService.findAll()).thenReturn(Arrays.asList(userDto1, userDto2));
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[0].userName", is(TEST_USER_NAME)))
                .andExpect(jsonPath("$[1].userName", is(TEST_USER_NAME)));
        Mockito.verify(userService).findAll();
    }

    @Test
    public void testAddUser() throws Exception {
        Mockito.doNothing().when(userService).create(any(UserDto.class));

        String requestDto = "  {\n" +
                "    \"userName\": \"" + TEST_USER_NAME + "\",\n" +
                "    \"passWord\": \"" + TEST_USER_PASS + "\",\n" +
                "    \"active\": true\n" +
                "  }";
        mockMvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON).content(requestDto))
                .andDo(print())
                .andExpect(status().isCreated());
        Mockito.verify(userService).create(any(UserDto.class));
    }

    @Test
    public void testUpdateRole() throws Exception {
        Mockito.doNothing().when(userService).update(any(UserDto.class));
        String requestDto = "  {\n" +
                "\t\"id\": 2,\n" +
                "    \"userName\": \"" + TEST_USER_NAME + "\",\n" +
                "    \"passWord\": \"" + TEST_USER_PASS + "\",\n" +
                "    \"active\": true\n" +
                "  }";
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON).content(requestDto))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(userService).update(any(UserDto.class));
    }

    @Test
    public void testDeleteRoleById() throws Exception {
        Mockito.doNothing().when(userService).remove(1L);
        mockMvc.perform(delete("/users/1"))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(userService).remove(1L);
    }
}
