package com.himashana.dkn.dkn_backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tools.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String contactNumber;
     @Column(nullable = false)
    private Integer permissionLevel;
     @Column(nullable = false)
    private String designation;
     @Column(nullable = false)
    private String officeLocation;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", nullable = false)
    private JsonNode performanceMetrics;

    @Column(nullable = false)
    private String responsibility;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", nullable = false)
    private List<String> expertDomains;

    private Integer yearsOfExperience;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal sessionHoursDelivered;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<WorkspaceAccess> workspaceAccessList = new ArrayList<>();
}
