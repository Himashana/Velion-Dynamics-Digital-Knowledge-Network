package com.himashana.dkn.dkn_backend.comment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.himashana.dkn.dkn_backend.comment.model.Comment;
import com.himashana.dkn.dkn_backend.comment.repository.CommentRepository;
import com.himashana.dkn.dkn_backend.content.model.Content;
import com.himashana.dkn.dkn_backend.content.repository.ContentRepository;
import com.himashana.dkn.dkn_backend.dto.ApiResponse;
import com.himashana.dkn.dkn_backend.user.dto.UserDto;
import com.himashana.dkn.dkn_backend.user.model.AppUser;
import com.himashana.dkn.dkn_backend.user.repository.AppUserRepository;
import com.himashana.dkn.dkn_backend.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
     @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ContentRepository contentRepository;

    private final UserService userService;

    // Add comment to content
    public ResponseEntity<ApiResponse> addComment(Comment comment, Long contentId, Authentication authentication){
        Content content = contentRepository.findById(contentId).orElse(null);
        if(content == null){
            return ResponseEntity.ok(new ApiResponse("Content not found"));
        }

        // Fetch current user
        UserDto currentUser = userService.getCurrentUser(authentication);

        AppUser user = appUserRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + currentUser.getUserId()));

        Comment newComment = new Comment();
        newComment.setContent(content);
        newComment.setComment(comment.getComment());
        newComment.setCommentedBy(user);
        newComment.setTimestamp(java.time.LocalDateTime.now().toString());
        newComment.setDeleted(false);

        // Save comment
        commentRepository.save(newComment);
        return ResponseEntity.ok(new ApiResponse("Comment added successfully") );
    }

    // Get a comment by id
    public ResponseEntity<Comment> getComment(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if(comment == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(comment);
    }

    // Get comments for content
    public ResponseEntity<Iterable<Comment>> getAllComments(Long contentId){
        Content content = contentRepository.findById(contentId).orElse(null);
        if(content == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(commentRepository.findByContentAndIsDeletedFalse(content));
    }

    // Soft delete comment
    public ResponseEntity<ApiResponse> deleteComment(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if(comment == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Comment not found"));
        }
        comment.setDeleted(true);
        commentRepository.save(comment);
        return ResponseEntity.ok(new ApiResponse("Comment deleted successfully"));
    }
}
