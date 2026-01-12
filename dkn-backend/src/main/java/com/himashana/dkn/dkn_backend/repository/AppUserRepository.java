package com.himashana.dkn.dkn_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himashana.dkn.dkn_backend.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    
}
