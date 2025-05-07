package com.cloud.app.spring_company_auth_serivce.configuration;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfiguration {
    @Bean
    public Keycloak keycloak() {
        //В этом конструкторе мы настраиваем
        //URL-адрес сервера, задаем ранее упомянутые
        //имена области и клиента, а также
        //предоставляем правильные учетные данные.
        return KeycloakBuilder.builder()
          .serverUrl("http://localhost:8080")
          .realm("master")
          .clientId("admin-cli")
          .grantType(OAuth2Constants.PASSWORD)
          .username("admin")
          .password("lol1234lol1234")
          .build();
    }
}
