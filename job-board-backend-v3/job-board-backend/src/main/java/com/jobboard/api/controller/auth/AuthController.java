package com.jobboard.api.controller.auth;

import com.jobboard.api.dto.auth.LoginRequest;
import com.jobboard.api.dto.auth.LoginResponse;
import com.jobboard.api.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {

        return authenticationService.login(
                request.getEmail(),
                request.getPassword()
        );
    }
}