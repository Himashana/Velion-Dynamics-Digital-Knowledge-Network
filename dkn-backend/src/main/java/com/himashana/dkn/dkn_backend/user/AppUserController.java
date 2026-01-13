package com.himashana.dkn.dkn_backend.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.himashana.dkn.dkn_backend.dto.ApiResponse;

import lombok.RequiredArgsConstructor;
import com.himashana.dkn.dkn_backend.user.model.AppUser;
import com.himashana.dkn.dkn_backend.user.service.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class AppUserController {
    private final UserService userService;

    // Register User
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody AppUser appUser) {
        return userService.registerUser(appUser);
    }
}
