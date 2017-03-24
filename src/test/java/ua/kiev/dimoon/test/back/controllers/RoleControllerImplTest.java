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
import ua.kiev.dimoon.test.back.controllers.interfaces.RoleController;
import ua.kiev.dimoon.test.back.dao.model.dto.RoleDto;
import ua.kiev.dimoon.test.back.services.interfaces.RoleService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.kiev.dimoon.test.back.TestObjectFactory.TEST_ROLE_NAME;
import static ua.kiev.dimoon.test.back.TestObjectFactory.createRoleDto;

/**
 * Created by admin on 23.03.2017.
 */
public class RoleControllerImplTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController = new RoleControllerImpl();

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    public void testGetAllRoles() throws Exception {
        RoleDto roleDto1 = createRoleDto(1, TEST_ROLE_NAME);
        RoleDto roleDto2 = createRoleDto(2, TEST_ROLE_NAME);
        when(roleService.findAll()).thenReturn(Arrays.asList(roleDto1, roleDto2));
        mockMvc.perform(get("/roles"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[0].name", is(TEST_ROLE_NAME)))
                .andExpect(jsonPath("$[1].name", is(TEST_ROLE_NAME)));
        Mockito.verify(roleService).findAll();
    }

    @Test
    public void testAddRole() throws Exception {
        Mockito.doNothing().when(roleService).create(any(RoleDto.class));

        String requestDto = "{\n" +
                "\t\"name\": \"" + TEST_ROLE_NAME + "\"\n" +
                "}";
        mockMvc.perform(put("/roles")
                .contentType(MediaType.APPLICATION_JSON).content(requestDto))
                .andDo(print())
                .andExpect(status().isCreated());
        Mockito.verify(roleService).create(any(RoleDto.class));
    }

    @Test
    public void testUpdateRole() throws Exception {
        Mockito.doNothing().when(roleService).update(any(RoleDto.class));
        String requestDto = "{\n" +
                "\t\"id\": 2,\n" +
                "\t\"name\": \"" + TEST_ROLE_NAME + "\"\n" +
                "}";
        mockMvc.perform(post("/roles")
                .contentType(MediaType.APPLICATION_JSON).content(requestDto))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(roleService).update(any(RoleDto.class));
    }

    @Test
    public void testDeleteRoleById() throws Exception {
        Mockito.doNothing().when(roleService).remove(1);
        mockMvc.perform(delete("/roles/1"))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(roleService).remove(1);
    }
}
