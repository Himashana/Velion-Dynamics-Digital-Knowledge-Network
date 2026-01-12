package com.himashana.dkn.dkn_backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private Integer permissionLevel;

    @Column(nullable = false)
    private String invitedDate;

    @Column(nullable = true)
    private Long invitedBy;
}
