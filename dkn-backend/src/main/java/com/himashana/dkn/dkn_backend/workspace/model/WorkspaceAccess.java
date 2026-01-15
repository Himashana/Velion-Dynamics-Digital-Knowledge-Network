package com.himashana.dkn.dkn_backend.workspace.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.himashana.dkn.dkn_backend.user.model.AppUser;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Getter
@Setter
@ToString

@Entity
public class WorkspaceAccess {
    @EmbeddedId
    private WorkspaceAccessId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne
    @MapsId("workspaceId")
    @JoinColumn(name = "workspace_id")
    private DigitalWorkspace digitalWorkspace;

    @Column(nullable = false)
    private Integer permissionLevel; // 1 - Read, 2 - Write

    @Column(nullable = false)
    private String invitedDate;

    @Column(nullable = true)
    private Long invitedBy;
}
