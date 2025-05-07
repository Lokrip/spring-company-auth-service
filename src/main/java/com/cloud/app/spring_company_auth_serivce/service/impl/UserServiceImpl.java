package com.cloud.app.spring_company_auth_serivce.service.impl;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import com.cloud.app.spring_company_auth_serivce.dto.UserDTO;
import com.cloud.app.spring_company_auth_serivce.service.KeycloakUserService;
import com.cloud.app.spring_company_auth_serivce.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final KeycloakUserService keycloakUserService;

    public UserServiceImpl(KeycloakUserService keycloakUserService) {
        this.keycloakUserService = keycloakUserService;
    }

    @Override
    public UserRepresentation createUser(UserDTO userDTO) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        //setEnabled(true) в Keycloak в классе UserRepresentation включает или выключает пользователя в системе.
        user.setEnabled(true);

        keycloakUserService.createUser(user);

        UserRepresentation user_created = keycloakUserService.getUsersResource()
            .search(userDTO.username())
            .get(0);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        //true: Временный пароль.
        //false: Постоянный пароль.
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setValue(userDTO.password());

        keycloakUserService.getUsersResource()
            .get(user_created.getId())
            .resetPassword(credentialRepresentation);

        return user_created;
    }

}
