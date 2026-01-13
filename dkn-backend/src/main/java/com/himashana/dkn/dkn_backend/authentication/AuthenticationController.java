package com.himashana.dkn.dkn_backend.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.himashana.dkn.dkn_backend.authentication.requests.LoginRequest;
import com.himashana.dkn.dkn_backend.authentication.service.AuthService;
import com.himashana.dkn.dkn_backend.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

import com.himashana.dkn.dkn_backend.user.repository.AppUserRepository;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    AppUserRepository appUserRepository;

    private final AuthService authService;

    // Authenticate User
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }
}
