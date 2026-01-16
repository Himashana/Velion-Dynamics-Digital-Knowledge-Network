package com.himashana.dkn.dkn_backend.content.model;

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
public class ContentAccess {
    @EmbeddedId
    private ContentAccessId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne
    @MapsId("contentId")
    @JoinColumn(name = "content_id")
    private Content content;

    @Column(nullable = false)
    private Integer permissionLevel; // 1 - Read, 2 - Write

    @Column(nullable = false)
    private String invitedDate;

    @Column(nullable = true)
    private Long invitedBy;
}
