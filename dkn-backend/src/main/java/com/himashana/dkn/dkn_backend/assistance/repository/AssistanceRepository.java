package com.himashana.dkn.dkn_backend.assistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himashana.dkn.dkn_backend.assistance.model.Assistance;

public interface AssistanceRepository extends JpaRepository<Assistance, Long> {
    
}
