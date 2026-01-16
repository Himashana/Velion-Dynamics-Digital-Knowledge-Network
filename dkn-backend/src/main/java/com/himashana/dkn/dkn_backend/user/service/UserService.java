package com.himashana.dkn.dkn_backend.user.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
    public ResponseEntity<ApiResponse> createUser(AppUser appUser) {
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
            newUser.setCreatedAt(java.time.LocalDate.now().toString());

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

    // Get user by ID
    public ResponseEntity<AppUser> getUserById(Long userId) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        appUser.setPassword(null); // Hide password
        return ResponseEntity.ok(appUser);
    }

    // Get all users
    public ResponseEntity<Iterable<AppUser>> getAllUsers() {
        Iterable<AppUser> users = appUserRepository.findAll();

        // Hide passwords
        for (AppUser user : users) {
            user.setPassword(null);
        }

        return ResponseEntity.ok(users);
    }

    // Update user
    public ResponseEntity<ApiResponse> updateUser(Long userId, AppUser updatedUser) {
        AppUser existingUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        existingUser.setName(updatedUser.getName());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setContactNumber(updatedUser.getContactNumber());
        existingUser.setPermissionLevel(updatedUser.getPermissionLevel());
        existingUser.setDesignation(updatedUser.getDesignation());
        existingUser.setOfficeLocation(updatedUser.getOfficeLocation());
        existingUser.setPerformanceMetrics(updatedUser.getPerformanceMetrics());
        existingUser.setResponsibility(updatedUser.getResponsibility());
        existingUser.setExpertDomains(updatedUser.getExpertDomains());
        existingUser.setYearsOfExperience(updatedUser.getYearsOfExperience());
        existingUser.setSessionHoursDelivered(updatedUser.getSessionHoursDelivered());

        appUserRepository.save(existingUser);

        ApiResponse apiResponse = new ApiResponse("User updated successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    // Increment years of experience based on the createdAt date automatically in each week
    @Scheduled(cron = "0 0 0 * * MON") // Every Monday at midnight
    public void incrementYearsOfExperience() {
        Iterable<AppUser> users = appUserRepository.findAll();
        for (AppUser user : users) {
            if (user.getCreatedAt() != null) {
                int createdYear = Integer.parseInt(user.getCreatedAt().split("-")[0]);
                int currentYear = java.time.LocalDate.now().getYear();
                int experience = currentYear - createdYear;
                user.setYearsOfExperience(experience);
                appUserRepository.save(user);
            }
        }
    }

    // Increment session hours delivered
    public void incrementSessionHoursDelivered(Long userId, double hours) {
        AppUser existingUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        double currentHours = existingUser.getSessionHoursDelivered() != null ? existingUser.getSessionHoursDelivered().doubleValue() : 0.0;
        existingUser.setSessionHoursDelivered(java.math.BigDecimal.valueOf(currentHours + hours));

        appUserRepository.save(existingUser);
    }

    // Delete user
    public ResponseEntity<ApiResponse> deleteUser(Long userId) {
        AppUser existingUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        appUserRepository.delete(existingUser);

        ApiResponse apiResponse = new ApiResponse("User deleted successfully.");
        return ResponseEntity.ok(apiResponse);
    }
}