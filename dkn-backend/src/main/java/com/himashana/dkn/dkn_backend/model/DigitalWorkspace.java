package com.himashana.dkn.dkn_backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;

@Getter
@Setter
@ToString

@Entity
public class DigitalWorkspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workspaceId;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = true)
    private String description;

    private boolean isDeleted;

    @OneToMany(mappedBy = "digitalWorkspace")
    @JsonIgnore
    private List<WorkspaceAccess> workspaceAccessList = new ArrayList<>();
}
