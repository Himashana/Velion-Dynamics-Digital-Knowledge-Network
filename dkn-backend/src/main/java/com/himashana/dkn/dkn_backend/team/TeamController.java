package com.himashana.dkn.dkn_backend.team;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.himashana.dkn.dkn_backend.dto.ApiResponse;
import com.himashana.dkn.dkn_backend.team.model.Team;
import com.himashana.dkn.dkn_backend.team.service.TeamService;

import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams")
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<ApiResponse> createTeam(@RequestBody Team team) {
        return teamService.createTeam(team);
    }

    @PostMapping("/{teamId}/assign/{staffMemberId}")
    public ResponseEntity<ApiResponse> grantTeamAccess(@PathVariable Long teamId, @PathVariable Long staffMemberId, Authentication authentication) {
        return teamService.grantTeamAccess(teamId, staffMemberId, authentication);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeam(@PathVariable Long id) {
        return teamService.getTeam(id);
    }

    @GetMapping
    public ResponseEntity<Iterable<Team>> getAllTeams() {
        return teamService.getAllTeams();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateTeam(@PathVariable Long id, @RequestBody Team team) {
        return teamService.updateTeam(id, team);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTeam(@PathVariable Long id) {
        return teamService.deleteTeam(id);
    }

    @DeleteMapping("/{teamId}/remove/{staffMemberId}")
    public ResponseEntity<ApiResponse> revokeTeamAccess(@PathVariable Long teamId, @PathVariable Long staffMemberId) {
        return teamService.revokeTeamAccess(teamId, staffMemberId);
    }
}
