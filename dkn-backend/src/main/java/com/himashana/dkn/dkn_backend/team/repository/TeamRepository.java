package com.himashana.dkn.dkn_backend.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himashana.dkn.dkn_backend.team.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Iterable<Team> findAllByIsDeletedFalse();
}
