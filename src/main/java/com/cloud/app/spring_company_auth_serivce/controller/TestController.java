package com.cloud.app.spring_company_auth_serivce.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tests")
public class TestController {

    @GetMapping
    public ResponseEntity<String> test(Principal principal) {
        return new ResponseEntity<>("Test Start", HttpStatus.OK);
    }
}
