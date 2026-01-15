package com.himashana.dkn.dkn_backend.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himashana.dkn.dkn_backend.content.model.Content;

public interface ContentRepository extends JpaRepository<Content, Long> {
    Iterable<Content> findAllByIsDeletedFalse();
}
