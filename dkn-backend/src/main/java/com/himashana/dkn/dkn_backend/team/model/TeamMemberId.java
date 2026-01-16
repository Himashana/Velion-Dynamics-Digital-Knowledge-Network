package com.himashana.dkn.dkn_backend.team.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberId implements Serializable {
    private Long teamId;
    private Long staffMemberId;
}
