package com.himashana.dkn.dkn_backend.workspace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.himashana.dkn.dkn_backend.dto.ApiResponse;
import com.himashana.dkn.dkn_backend.user.model.AppUser;
import com.himashana.dkn.dkn_backend.user.repository.AppUserRepository;
import com.himashana.dkn.dkn_backend.workspace.model.DigitalWorkspace;
import com.himashana.dkn.dkn_backend.workspace.repository.DigitalWorkspaceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkspaceService {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    DigitalWorkspaceRepository digitalWorkspaceRepository;

    // Create a new digital workspace
    public ResponseEntity<ApiResponse> createWorkspace(DigitalWorkspace digitalWorkspace) {
        ApiResponse apiResponse = new ApiResponse("");

        // Set default values
        digitalWorkspace.setWorkspaceId(null); // Ensure the ID is null for new entity creation to auto-generate
        digitalWorkspace.setDeleted(false);

        // Save the new workspace
        digitalWorkspaceRepository.save(digitalWorkspace);
        apiResponse.setResponse("Workspace created successfully.");

        return ResponseEntity.ok(apiResponse);
    }

    // Retrieve a digital workspace by ID
    public ResponseEntity<DigitalWorkspace> getWorkspace(Long workspaceId) {
        DigitalWorkspace digitalWorkspace = digitalWorkspaceRepository.findById(workspaceId)
                .filter(workspace -> !workspace.isDeleted())
                .orElseThrow(() -> new RuntimeException("Workspace not found with id: " + workspaceId));
        return ResponseEntity.ok(digitalWorkspace);
    }

    // Retrieve all digital workplaces
    public ResponseEntity<Iterable<DigitalWorkspace>> getAllWorkspaces(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Access denied.");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Fetch user from the database
        AppUser appUser = appUserRepository
                .findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));


        Iterable<DigitalWorkspace> workspaces;

        if (appUser.getPermissionLevel() == 1) {
            // All workspaces access for leadership level
            workspaces = digitalWorkspaceRepository.findAllByIsDeletedFalse();
        } else {
            // Limited workspaces access for other users
            workspaces = digitalWorkspaceRepository.findAllByIsDeletedFalseAndCurrentUser(appUser.getUserId());
        }

        return ResponseEntity.ok(workspaces);
    }

    // Update an existing digital workspace
    public ResponseEntity<ApiResponse> updateWorkspace(DigitalWorkspace digitalWorkspace) {
        ApiResponse apiResponse = new ApiResponse("");

        // Check if the workspace exists
        if (!digitalWorkspaceRepository.existsById(digitalWorkspace.getWorkspaceId())) {
            apiResponse.setResponse("Workspace not found with id: " + digitalWorkspace.getWorkspaceId());
            return ResponseEntity.badRequest().body(apiResponse);
        }

        // Update the workspace
        digitalWorkspaceRepository.save(digitalWorkspace);
        apiResponse.setResponse("Workspace updated successfully.");

        return ResponseEntity.ok(apiResponse);
    }

    // Soft delete a digital workspace
    public ResponseEntity<ApiResponse> deleteWorkspace(Long workspaceId) {
        ApiResponse apiResponse = new ApiResponse("");

        DigitalWorkspace digitalWorkspace = digitalWorkspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("Workspace not found with id: " + workspaceId));

        // Soft delete by setting the deleted flag to true
        digitalWorkspace.setDeleted(true);
        digitalWorkspaceRepository.save(digitalWorkspace);

        apiResponse.setResponse("Workspace deleted successfully.");
        return ResponseEntity.ok(apiResponse);
    }
}
