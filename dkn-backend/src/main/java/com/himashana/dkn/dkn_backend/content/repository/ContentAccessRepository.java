package com.himashana.dkn.dkn_backend.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himashana.dkn.dkn_backend.content.model.ContentAccess;
import com.himashana.dkn.dkn_backend.content.model.ContentAccessId;

public interface ContentAccessRepository extends JpaRepository<ContentAccess, ContentAccessId> {   }
