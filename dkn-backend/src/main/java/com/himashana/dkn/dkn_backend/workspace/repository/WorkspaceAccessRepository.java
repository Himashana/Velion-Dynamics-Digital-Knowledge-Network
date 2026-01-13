package com.himashana.dkn.dkn_backend.workspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himashana.dkn.dkn_backend.workspace.model.WorkspaceAccess;
import com.himashana.dkn.dkn_backend.workspace.model.WorkspaceAccessId;

public interface WorkspaceAccessRepository extends JpaRepository<WorkspaceAccess, WorkspaceAccessId> {
    
}
