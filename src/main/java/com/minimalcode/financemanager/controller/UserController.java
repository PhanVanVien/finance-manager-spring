package com.minimalcode.financemanager.controller;

import com.minimalcode.financemanager.dto.auth.request.RegisterRequest;
import com.minimalcode.financemanager.model.user.User;
import com.minimalcode.financemanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest user) {
        userService.register(user);
        return ResponseEntity.ok("User is valid");
    }
}
