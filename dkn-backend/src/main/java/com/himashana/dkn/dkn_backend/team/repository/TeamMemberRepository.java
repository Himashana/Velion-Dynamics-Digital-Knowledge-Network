package com.himashana.dkn.dkn_backend.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himashana.dkn.dkn_backend.team.model.TeamMember;
import com.himashana.dkn.dkn_backend.team.model.TeamMemberId;

public interface TeamMemberRepository extends JpaRepository<TeamMember, TeamMemberId> {
    
}
