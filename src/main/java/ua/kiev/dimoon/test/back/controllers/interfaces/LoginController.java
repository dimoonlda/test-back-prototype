package ua.kiev.dimoon.test.back.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import ua.kiev.dimoon.test.back.dao.model.dto.LoginDto;


public interface LoginController {
    ResponseEntity<String> getToken(LoginDto loginDto);
}
