package com.himashana.dkn.dkn_backend.workspace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.himashana.dkn.dkn_backend.dto.ApiResponse;
import com.himashana.dkn.dkn_backend.user.model.AppUser;
import com.himashana.dkn.dkn_backend.user.repository.AppUserRepository;
import com.himashana.dkn.dkn_backend.workspace.model.DigitalWorkspace;
import com.himashana.dkn.dkn_backend.workspace.model.WorkspaceAccess;
import com.himashana.dkn.dkn_backend.workspace.model.WorkspaceAccessId;
import com.himashana.dkn.dkn_backend.workspace.repository.DigitalWorkspaceRepository;
import com.himashana.dkn.dkn_backend.workspace.repository.WorkspaceAccessRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkspaceAccessService {
    @Autowired
    DigitalWorkspaceRepository digitalWorkspaceRepository;

    @Autowired
    WorkspaceAccessRepository workspaceAccessRepository;

    @Autowired
    AppUserRepository appUserRepository;

    // assign workspace to user
    public ResponseEntity<ApiResponse> assignWorkspaceToUser(WorkspaceAccess workspaceAccess){
        DigitalWorkspace workspace = digitalWorkspaceRepository.findById(workspaceAccess.getId().getWorkspaceId()).orElse(null);
        if(workspace == null){
            return ResponseEntity.ok(new ApiResponse("Workspace not found"));
        }

        AppUser user = appUserRepository.findById(workspaceAccess.getId().getUserId()).orElse(null);
        if(user == null){
            return ResponseEntity.ok(new ApiResponse("User not found"));
        }

        WorkspaceAccess newWorkspaceAccess = new WorkspaceAccess();
        newWorkspaceAccess.setId(new WorkspaceAccessId(workspace.getWorkspaceId(), user.getUserId()));
        newWorkspaceAccess.setDigitalWorkspace(workspace);
        newWorkspaceAccess.setUser(user);
        newWorkspaceAccess.setPermissionLevel(workspaceAccess.getPermissionLevel());
        newWorkspaceAccess.setInvitedDate(java.time.LocalDate.now().toString());
        newWorkspaceAccess.setInvitedBy(workspaceAccess.getInvitedBy());

        workspaceAccessRepository.save(newWorkspaceAccess);
        return ResponseEntity.ok(new ApiResponse("Workspace assigned to user successfully") );
    }

    // Remove workspace access from user
    public ResponseEntity<ApiResponse> removeAccess(WorkspaceAccessId workspaceAccessId){
        WorkspaceAccess workspaceAccess = workspaceAccessRepository.findById(workspaceAccessId).orElse(null);
        if(workspaceAccess == null){
            return ResponseEntity.ok(new ApiResponse("Workspace access not found"));
        }

        workspaceAccessRepository.delete(workspaceAccess);
        return ResponseEntity.ok(new ApiResponse("Workspace access removed successfully"));
    }
}
