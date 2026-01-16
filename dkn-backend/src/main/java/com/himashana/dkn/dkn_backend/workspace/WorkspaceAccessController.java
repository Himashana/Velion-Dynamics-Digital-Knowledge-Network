package com.himashana.dkn.dkn_backend.workspace;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.himashana.dkn.dkn_backend.dto.ApiResponse;

import lombok.RequiredArgsConstructor;
import com.himashana.dkn.dkn_backend.workspace.model.WorkspaceAccess;
import com.himashana.dkn.dkn_backend.workspace.model.WorkspaceAccessId;
import com.himashana.dkn.dkn_backend.workspace.service.WorkspaceAccessService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workspace-access")
public class WorkspaceAccessController {
    private final WorkspaceAccessService workspaceAccessService;
    
    @PostMapping
    public ResponseEntity<ApiResponse> assignWorkspaceToUser(@RequestBody WorkspaceAccess workspaceAccess) {
        return workspaceAccessService.assignWorkspaceToUser(workspaceAccess);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> removeAccess(@RequestBody WorkspaceAccessId workspaceAccessId) {
        return workspaceAccessService.removeAccess(workspaceAccessId);
    }
}
