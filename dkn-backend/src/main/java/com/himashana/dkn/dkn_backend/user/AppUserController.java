package com.himashana.dkn.dkn_backend.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.himashana.dkn.dkn_backend.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

import com.himashana.dkn.dkn_backend.user.dto.UserDto;
import com.himashana.dkn.dkn_backend.user.model.AppUser;
import com.himashana.dkn.dkn_backend.user.service.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class AppUserController {
    private final UserService userService;

    // Create User
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> createUser(@RequestBody AppUser appUser) {
        return userService.createUser(appUser);
    }

    // Get Current User
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        UserDto currentUser = userService.getCurrentUser(authentication);
        return ResponseEntity.ok(currentUser);
    }

    // Get User by ID
    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Get All Users
    @GetMapping
    public ResponseEntity<Iterable<AppUser>> getAllUsers() {
        return userService.getAllUsers();
    }

    // Update User
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @RequestBody AppUser appUser) {
        return userService.updateUser(id, appUser);
    }

    // Increment session hours delivered
    @PutMapping("/{id}/increment-session-hours")
    public ResponseEntity<ApiResponse> incrementSessionHoursDelivered(@PathVariable Long id, @RequestParam double hours) {
        userService.incrementSessionHoursDelivered(id, hours);
        ApiResponse apiResponse = new ApiResponse("Session hours delivered incremented successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}