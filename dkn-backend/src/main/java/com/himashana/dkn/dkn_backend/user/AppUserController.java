package com.himashana.dkn.dkn_backend.user;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.himashana.dkn.dkn_backend.authentication.service.JwtService;
import com.himashana.dkn.dkn_backend.dto.ApiResponse;

import lombok.RequiredArgsConstructor;
import com.himashana.dkn.dkn_backend.user.model.AppUser;
import com.himashana.dkn.dkn_backend.user.repository.AppUserRepository;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class AppUserController {
    @Autowired
    AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // Register User
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody AppUser appUser) {
        Optional<AppUser> existingUser = appUserRepository.findByEmail(appUser.getEmail());

        ApiResponse apiResponse = new ApiResponse("");

        if(!existingUser.isPresent()){ // If the user does not exists already,
            AppUser newUser = new AppUser();

            newUser.setName(appUser.getName());
            newUser.setAddress(appUser.getAddress());
            newUser.setEmail(appUser.getEmail());
            newUser.setContactNumber(appUser.getContactNumber());
            newUser.setPermissionLevel(appUser.getPermissionLevel());
            newUser.setDesignation(appUser.getDesignation());
            newUser.setOfficeLocation(appUser.getOfficeLocation());
            newUser.setPerformanceMetrics(appUser.getPerformanceMetrics());
            newUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

            // Save the new user
            appUserRepository.save(newUser);
            var token = jwtService.createToken(new HashMap<>(), newUser); // Create a new jwt token for the user
            apiResponse.setResponse("User registered successfully.");
            apiResponse.setToken(token);
        } else { // If the user exists
            apiResponse.setErrorCode("DUPLICATED_INFOMARTION");
            apiResponse.setResponse("User with the given email already exists.");
        }
        return ResponseEntity.ok(apiResponse);
    }
}
