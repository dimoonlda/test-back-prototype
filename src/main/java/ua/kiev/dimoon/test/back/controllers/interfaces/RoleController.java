package ua.kiev.dimoon.test.back.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import ua.kiev.dimoon.test.back.dao.model.dto.RoleDto;

import java.util.List;

/**
 * Created by admin on 22.03.2017.
 */
public interface RoleController {
    ResponseEntity<List<RoleDto>> getAllRoles();
    ResponseEntity addRole(RoleDto roleDto);
    ResponseEntity updateRole(RoleDto roleDto);
    ResponseEntity deleteRoleById(Integer id);
}
