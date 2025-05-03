package com.cloud.app.spring_company_auth_serivce.configuration;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Включаем Resourse server с его форматом серелизаций токена
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http
                .authorizeHttpRequests(c -> c
                    .requestMatchers("/error").permitAll()
                    .requestMatchers("/manager.html").hasRole("MANAGER")
                    .anyRequest().authenticated())
                .build();
    }


    @Bean
    //Из токена берем роли, чтобы потом
    //spring security их обработал и давал по ним доступ,
    //OAuth не может предскозать где брать роли в токене payload.
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var convertor = new JwtAuthenticationConverter();
        var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        //говорим Spring Security используй значение поля preferred_username в
        //jwt как имя пользователяу principal
        //а не например sub которое по умолчанию в токене
        convertor.setPrincipalClaimName("preferred_username");
        convertor.setJwtGrantedAuthoritiesConverter(jwt -> {
            //получаем стандартные прова и scope
            var authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
            //получаем роли изи поля realm_access.roles
            var roles = (List<String>) jwt.getClaimAsMap("realm_access").get("roles");

            System.out.println(authorities); // -> [SCOPE_profile, SCOPE_email]
            System.out.println(roles); // -> [offline_access, ROLE_MANAGER, default-roles-barcode, uma_authorization]

            //объединяем их чтобы spring security по ним делал авторизацию
            return Stream.concat(authorities.stream(),
                roles
                    .stream()
                    .filter(role -> role.startsWith("ROLE_"))
                    .map(SimpleGrantedAuthority::new)
                    .map(GrantedAuthority.class::cast))
                .toList();
        });

        return convertor;
    }
}
