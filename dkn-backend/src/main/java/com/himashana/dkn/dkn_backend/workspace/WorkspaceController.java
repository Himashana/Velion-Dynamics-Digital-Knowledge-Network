package com.himashana.dkn.dkn_backend.workspace;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.himashana.dkn.dkn_backend.dto.ApiResponse;

import lombok.RequiredArgsConstructor;
import com.himashana.dkn.dkn_backend.workspace.model.DigitalWorkspace;
import com.himashana.dkn.dkn_backend.workspace.service.WorkspaceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/digital-workspaces")
public class WorkspaceController {
    private final WorkspaceService workspaceService;

    @PostMapping
    public ResponseEntity<ApiResponse> createWorkspace(@RequestBody DigitalWorkspace digitalWorkspace) {
        return workspaceService.createWorkspace(digitalWorkspace);
    }
}
