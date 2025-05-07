package com.cloud.app.spring_company_auth_serivce.controller;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.app.spring_company_auth_serivce.dto.UserDTO;
import com.cloud.app.spring_company_auth_serivce.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService authService;

    public UserController(UserService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserRepresentation> register(@RequestBody UserDTO userDTO) {
        UserRepresentation user_created = authService.createUser(userDTO);
        return new ResponseEntity<>(user_created, HttpStatus.CREATED);
    }
}
