package com.cloud.app.spring_company_auth_serivce.service;

import org.keycloak.representations.idm.UserRepresentation;

import com.cloud.app.spring_company_auth_serivce.dto.UserDTO;

public interface UserService {
    UserRepresentation createUser(UserDTO userDTO);
}
