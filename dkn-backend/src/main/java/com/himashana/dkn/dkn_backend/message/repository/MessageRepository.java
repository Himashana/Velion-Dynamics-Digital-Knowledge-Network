package com.himashana.dkn.dkn_backend.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himashana.dkn.dkn_backend.message.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Iterable<Message> findAllByIsDeletedFalse();
}
