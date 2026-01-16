package com.himashana.dkn.dkn_backend.assistance.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
public class Assistance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String callUrl;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "arranged_by", nullable = false)
    private AppUser arrangedBy;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "arranged_to", nullable = false)
    private AppUser arrangedTo;

    private String timestamp;
}
