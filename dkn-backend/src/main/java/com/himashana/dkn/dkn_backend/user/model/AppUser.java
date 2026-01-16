package com.himashana.dkn.dkn_backend.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.himashana.dkn.dkn_backend.assistance.model.Assistance;
import com.himashana.dkn.dkn_backend.comment.model.Comment;
import com.himashana.dkn.dkn_backend.content.model.ContentAccess;
import com.himashana.dkn.dkn_backend.message.model.Message;
import com.himashana.dkn.dkn_backend.team.model.TeamMember;
import com.himashana.dkn.dkn_backend.user.enums.PermissionLevel;
import com.himashana.dkn.dkn_backend.workspace.model.WorkspaceAccess;

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
public class AppUser implements UserDetails {
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
    @Column(columnDefinition = "json", nullable = true)
    private Map<String, Object> performanceMetrics;

    private String responsibility;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", nullable = true)
    private List<String> expertDomains;

    private Integer yearsOfExperience;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal sessionHoursDelivered;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<WorkspaceAccess> workspaceAccessList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ContentAccess> contentAccessList = new ArrayList<>();

    @OneToMany(mappedBy = "commentedBy")
    @JsonIgnore
    private List<Comment> commentsList = new ArrayList<>();

    @OneToMany(mappedBy = "staffMember")
    @JsonIgnore
    private List<TeamMember> teamMemberList = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    private List<Message> sentMessagesList = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    @JsonIgnore
    private List<Message> receivedMessagesList = new ArrayList<>();

    @OneToMany(mappedBy = "arrangedBy")
    @JsonIgnore
    private List<Assistance> arrangedAssistanceByList = new ArrayList<>();

    @OneToMany(mappedBy = "arrangedTo")
    @JsonIgnore
    private List<Assistance> arrangedAssistanceToList = new ArrayList<>();
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        PermissionLevel permission =
        PermissionLevel.fromLevel(permissionLevel);

        return List.of(new SimpleGrantedAuthority(permission.asRole()));
    }
    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
