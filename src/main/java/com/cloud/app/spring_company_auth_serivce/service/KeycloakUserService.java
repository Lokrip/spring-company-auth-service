package com.cloud.app.spring_company_auth_serivce.service;

import java.util.List;

import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;

import jakarta.ws.rs.core.Response;

public interface KeycloakUserService {
    UsersResource getUsersResource();

    List<UserRepresentation> getAllUsers();

    UserRepresentation getUserById(String userid);

    Response createUser(UserRepresentation user);

    List<UserRepresentation> searchByEmail(String email);
}
