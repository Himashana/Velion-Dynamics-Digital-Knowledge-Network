package com.himashana.dkn.dkn_backend.team.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.himashana.dkn.dkn_backend.user.model.AppUser;
import com.himashana.dkn.dkn_backend.workspace.model.DigitalWorkspace;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;

@Getter
@Setter
@ToString

@Entity
public class TeamMember {
    @EmbeddedId
    private TeamMemberId id;

    @ManyToOne
    @MapsId("teamId")
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @MapsId("staffMemberId")
    @JoinColumn(name = "staff_member_id")
    private AppUser staffMember;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "invited_by", nullable = false)
    private AppUser invitedBy;
}
