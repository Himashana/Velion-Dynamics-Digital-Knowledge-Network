package com.himashana.dkn.dkn_backend.workspace;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.himashana.dkn.dkn_backend.dto.ApiResponse;

import lombok.RequiredArgsConstructor;
import com.himashana.dkn.dkn_backend.workspace.model.DigitalWorkspace;
import com.himashana.dkn.dkn_backend.workspace.service.WorkspaceService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/digital-workspaces")
public class WorkspaceController {
    private final WorkspaceService workspaceService;

    @PostMapping
    public ResponseEntity<ApiResponse> createWorkspace(Authentication authentication, @RequestBody DigitalWorkspace digitalWorkspace) {
        return workspaceService.createWorkspace(authentication, digitalWorkspace);
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<DigitalWorkspace> getWorkspace(@PathVariable Long workspaceId) {
        return workspaceService.getWorkspace(workspaceId);
    }

    @GetMapping
    public ResponseEntity<Iterable<DigitalWorkspace>> getAllWorkspaces() {
        return workspaceService.getAllWorkspaces();
    }

    @GetMapping("/me")
    public ResponseEntity<Iterable<DigitalWorkspace>> getAllWorkspacesCurrentUser(Authentication authentication) {
        return workspaceService.getAllWorkspacesCurrentUser(authentication);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateWorkspace(@RequestBody DigitalWorkspace digitalWorkspace) {
        return workspaceService.updateWorkspace(digitalWorkspace);
    }

    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<ApiResponse> deleteWorkspace(@PathVariable Long workspaceId) {
        return workspaceService.deleteWorkspace(workspaceId);
    }
}
