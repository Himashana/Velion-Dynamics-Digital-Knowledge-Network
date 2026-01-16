package com.himashana.dkn.dkn_backend.message.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.himashana.dkn.dkn_backend.team.model.Team;
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
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private AppUser sender;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = true)
    private AppUser receiver;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = true)
    private Team team;

    @Column(nullable = false)
    private String message;

    private String timestamp;
    private boolean isDeleted;
}
