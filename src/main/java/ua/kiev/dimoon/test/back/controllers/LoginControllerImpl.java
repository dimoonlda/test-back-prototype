package ua.kiev.dimoon.test.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.kiev.dimoon.test.back.controllers.interfaces.LoginController;
import ua.kiev.dimoon.test.back.dao.model.dto.LoginDto;
import ua.kiev.dimoon.test.back.services.interfaces.TokenService;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginControllerImpl implements LoginController {

    private TokenService tokenService;

    @Autowired
    public LoginControllerImpl setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
        return this;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getToken(@RequestBody @Valid LoginDto loginDto) {
        String token = tokenService.createToken(loginDto.getUserName(), loginDto.getPassWord())
                .orElseThrow(() -> new BadCredentialsException("Wrong user."));
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
