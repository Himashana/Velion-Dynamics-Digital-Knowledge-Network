package com.himashana.dkn.dkn_backend.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himashana.dkn.dkn_backend.comment.model.Comment;
import com.himashana.dkn.dkn_backend.content.model.Content;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Iterable<Comment> findByContentAndIsDeletedFalse(Content content);
}
