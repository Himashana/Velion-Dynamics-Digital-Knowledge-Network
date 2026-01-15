package com.himashana.dkn.dkn_backend.workspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.himashana.dkn.dkn_backend.workspace.model.DigitalWorkspace;

public interface DigitalWorkspaceRepository extends JpaRepository<DigitalWorkspace, Long> {
    Iterable<DigitalWorkspace> findAllByIsDeletedFalse();

    @Query("""
        SELECT w
        FROM DigitalWorkspace w
        JOIN w.workspaceAccessList wa
        WHERE wa.user.userId = :userId AND w.isDeleted = false
    """)
    Iterable<DigitalWorkspace> findAllByIsDeletedFalseAndCurrentUser(Long userId);
}
