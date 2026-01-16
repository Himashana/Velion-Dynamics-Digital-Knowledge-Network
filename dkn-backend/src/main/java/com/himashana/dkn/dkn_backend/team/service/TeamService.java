package com.himashana.dkn.dkn_backend.team.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.himashana.dkn.dkn_backend.dto.ApiResponse;
import com.himashana.dkn.dkn_backend.team.model.Team;
import com.himashana.dkn.dkn_backend.team.model.TeamMember;
import com.himashana.dkn.dkn_backend.team.model.TeamMemberId;
import com.himashana.dkn.dkn_backend.team.repository.TeamMemberRepository;
import com.himashana.dkn.dkn_backend.team.repository.TeamRepository;
import com.himashana.dkn.dkn_backend.user.dto.UserDto;
import com.himashana.dkn.dkn_backend.user.model.AppUser;
import com.himashana.dkn.dkn_backend.user.repository.AppUserRepository;
import com.himashana.dkn.dkn_backend.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Autowired
    AppUserRepository appUserRepository;

    private final UserService userService;
    

    // Create team
    public ResponseEntity<ApiResponse> createTeam(Team team) {
        teamRepository.save(team);
        return ResponseEntity.ok(new ApiResponse("Team created successfully"));
    }

    // Get team by id
    public ResponseEntity<Team> getTeam(Long id) {
        Team team = teamRepository.findById(id).orElse(null);
        if (team == null || team.isDeleted()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(team);
    }

    // Get all teams
    public ResponseEntity<Iterable<Team>> getAllTeams() {
        Iterable<Team> teams = teamRepository.findAllByIsDeletedFalse();
        return ResponseEntity.ok(teams);
    }

    // Update team
    public ResponseEntity<ApiResponse> updateTeam(Long id, Team updatedTeam) {
        Team team = teamRepository.findById(id).orElse(null);
        if (team == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Team not found"));
        }
        team.setName(updatedTeam.getName());
        teamRepository.save(team);
        return ResponseEntity.ok(new ApiResponse("Team updated successfully"));
    }

    // Grant team access to a user
    public ResponseEntity<ApiResponse> grantTeamAccess(Long teamId, Long staffMemberId, Authentication authentication) {
        Team team = teamRepository.findById(teamId).orElse(null);
        if (team == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Team not found"));
        }

        AppUser user = appUserRepository.findById(staffMemberId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found"));
        }

        // Fetch current user
        UserDto currentUser = userService.getCurrentUser(authentication);
        AppUser currentAppUser = appUserRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + currentUser.getUserId()));

        TeamMember teamMember = new TeamMember();
        teamMember.setId(new TeamMemberId(teamId, staffMemberId));
        teamMember.setTeam(team);
        teamMember.setStaffMember(user);
        teamMember.setInvitedBy(currentAppUser);

        // Save team member
        teamMemberRepository.save(teamMember);
        return ResponseEntity.ok(new ApiResponse("User granted access to team successfully"));
    }

    // Soft delete team
    public ResponseEntity<ApiResponse> deleteTeam(Long id) {
        Team team = teamRepository.findById(id).orElse(null);
        if (team == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Team not found"));
        }
        team.setDeleted(true);
        teamRepository.save(team);
        return ResponseEntity.ok(new ApiResponse("Team deleted successfully"));
    }

    // Revoke access from a user
    public ResponseEntity<ApiResponse> revokeTeamAccess(Long teamId, Long staffMemberId) {
        TeamMemberId teamMemberId = new TeamMemberId(teamId, staffMemberId);
        TeamMember teamMember = teamMemberRepository.findById(teamMemberId).orElse(null);
        if (teamMember == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Team member not found"));
        }
        teamMemberRepository.delete(teamMember);
        return ResponseEntity.ok(new ApiResponse("User access revoked from team successfully"));
    }
}
