package com.himashana.dkn.dkn_backend.user.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.himashana.dkn.dkn_backend.authentication.service.JwtService;
import com.himashana.dkn.dkn_backend.dto.ApiResponse;
import com.himashana.dkn.dkn_backend.user.dto.UserDto;
import com.himashana.dkn.dkn_backend.user.model.AppUser;
import com.himashana.dkn.dkn_backend.user.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // Register User
    public ResponseEntity<ApiResponse> registerUser(AppUser appUser) {
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

    // Get Current User
    public UserDto getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Access denied.");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Fetch user from the database
        AppUser appUser = appUserRepository
                .findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Return only the relevant user details in UserDto
        UserDto userDto = new UserDto();
        userDto.setUserId(appUser.getUserId());
        userDto.setName(appUser.getName());
        userDto.setAddress(appUser.getAddress());
        userDto.setEmail(appUser.getEmail());
        userDto.setContactNumber(appUser.getContactNumber());
        userDto.setPermissionLevel(appUser.getPermissionLevel());
        userDto.setDesignation(appUser.getDesignation());
        userDto.setOfficeLocation(appUser.getOfficeLocation());
        userDto.setPerformanceMetrics(appUser.getPerformanceMetrics());
        userDto.setResponsibility(appUser.getResponsibility());
        userDto.setExpertDomains(appUser.getExpertDomains());
        userDto.setYearsOfExperience(appUser.getYearsOfExperience());
        userDto.setSessionHoursDelivered(appUser.getSessionHoursDelivered());

        return userDto;
    }
}