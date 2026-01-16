package com.himashana.dkn.dkn_backend.comment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.himashana.dkn.dkn_backend.content.model.Content;
import com.himashana.dkn.dkn_backend.user.model.AppUser;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Getter
@Setter
@ToString

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "commented_by", nullable = false)
    private AppUser commentedBy;

    private String timestamp;
    private boolean isDeleted;
}
