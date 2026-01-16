package com.himashana.dkn.dkn_backend.comment;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.himashana.dkn.dkn_backend.comment.model.Comment;
import com.himashana.dkn.dkn_backend.comment.service.CommentService;
import com.himashana.dkn.dkn_backend.dto.ApiResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contents/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping(value = "/{contentId}/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> addComment(@PathVariable Long contentId, Authentication authentication, @RequestBody Comment comment) {
        return commentService.addComment(comment, contentId, authentication);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getComment(@PathVariable Long commentId) {
        return commentService.getComment(commentId);
    }

    @GetMapping("/content/{contentId}")
    public ResponseEntity<Iterable<Comment>> getAllComments(@PathVariable Long contentId) {
        return commentService.getAllComments(contentId);
    }

    @PutMapping("/{commentId}/delete")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }
}