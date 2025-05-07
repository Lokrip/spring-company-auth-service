package com.cloud.app.spring_company_auth_serivce.service.impl;

import java.util.List;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloud.app.spring_company_auth_serivce.service.KeycloakUserService;

import jakarta.ws.rs.core.Response;


@Service
public class KeycloakUserServiceImpl implements KeycloakUserService {
    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public KeycloakUserServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

    @Override
    public List<UserRepresentation> getAllUsers() {
        return getUsersResource().list();
    }

    @Override
    public UserRepresentation getUserById(String userid) {
        return getUsersResource()
            .get(userid)
            .toRepresentation();
    }

    @Override
    public List<UserRepresentation> searchByEmail(String email) {
        //boolean exact true указывает, должен ли поиск быть точным
        return getUsersResource().search(email, true);
    }

    @Override
    public Response createUser(UserRepresentation user) {
        Response response = getUsersResource().create(user);
        if(response.getStatus() != 201) {
            throw new RuntimeException("Не удалось создать пользователя: " + response.getStatus());
        }
        return response;
    }


}
