package com.himashana.dkn.dkn_backend.workspace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.himashana.dkn.dkn_backend.dto.ApiResponse;
import com.himashana.dkn.dkn_backend.workspace.model.DigitalWorkspace;
import com.himashana.dkn.dkn_backend.workspace.repository.DigitalWorkspaceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkspaceService {
    @Autowired
    DigitalWorkspaceRepository digitalWorkspaceRepository;

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
}
