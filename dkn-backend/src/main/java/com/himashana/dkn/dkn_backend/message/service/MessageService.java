package com.himashana.dkn.dkn_backend.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.himashana.dkn.dkn_backend.dto.ApiResponse;
import com.himashana.dkn.dkn_backend.message.model.Message;
import com.himashana.dkn.dkn_backend.message.repository.MessageRepository;
import com.himashana.dkn.dkn_backend.team.repository.TeamRepository;
import com.himashana.dkn.dkn_backend.user.dto.UserDto;
import com.himashana.dkn.dkn_backend.user.model.AppUser;
import com.himashana.dkn.dkn_backend.user.repository.AppUserRepository;
import com.himashana.dkn.dkn_backend.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MessageRepository messageRepository;

    private final UserService userService;

    // Send message to user or team
    public ResponseEntity<ApiResponse> sendMessage(Message message, Authentication authentication){
        // Fetch current user
        UserDto currentUser = userService.getCurrentUser(authentication);

        AppUser sender = appUserRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + currentUser.getUserId()));

        Message newMessage = new Message();
        newMessage.setSender(sender);
        newMessage.setReceiver(message.getReceiver());
        newMessage.setTeam(message.getTeam());
        newMessage.setMessage(message.getMessage());
        newMessage.setTimestamp(java.time.LocalDateTime.now().toString());
        newMessage.setDeleted(false);

        // Save message
        messageRepository.save(newMessage);
        return ResponseEntity.ok(new ApiResponse("Message sent successfully"));
    }

    // Get a message by id
    public ResponseEntity<Message> getMessage(Long messageId){
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + messageId));
        return ResponseEntity.ok(message);
    }

    // Get all messages
    public ResponseEntity<Iterable<Message>> getAllMessages(){
        Iterable<Message> messages = messageRepository.findAllByIsDeletedFalse();
        return ResponseEntity.ok(messages);
    }

    // Delete a message by id
    public ResponseEntity<ApiResponse> deleteMessage(Long messageId){
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + messageId));
        message.setDeleted(true);
        messageRepository.save(message);
        return ResponseEntity.ok(new ApiResponse("Message deleted successfully"));
    }
}