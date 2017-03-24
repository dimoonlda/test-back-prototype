package ua.kiev.dimoon.test.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kiev.dimoon.test.back.controllers.interfaces.RoleController;
import ua.kiev.dimoon.test.back.dao.model.dto.RoleDto;
import ua.kiev.dimoon.test.back.services.interfaces.RoleService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/roles")
public class RoleControllerImpl implements RoleController {

    private RoleService roleService;

    @Autowired
    public RoleControllerImpl setRoleService(RoleService roleService) {
        this.roleService = roleService;
        return this;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<RoleDto> roles = roleService.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);

    }

    @Override
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addRole(@RequestBody @Valid RoleDto roleDto) {
        roleService.create(roleDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateRole(@RequestBody @Valid RoleDto roleDto) {
        if (Objects.isNull(roleDto.getId())) {
            throw new IllegalArgumentException("field id is required.");
        }
        roleService.update(roleDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRoleById(@PathVariable("id") Integer id) {
        roleService.remove(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
