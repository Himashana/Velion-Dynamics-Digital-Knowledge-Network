package com.himashana.dkn.dkn_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himashana.dkn.dkn_backend.model.WorkspaceAccess;
import com.himashana.dkn.dkn_backend.model.WorkspaceAccessId;

public interface WorkspaceAccessRepository extends JpaRepository<WorkspaceAccess, WorkspaceAccessId> {
    
}
