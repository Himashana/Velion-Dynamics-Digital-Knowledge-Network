package com.himashana.dkn.dkn_backend.message;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.himashana.dkn.dkn_backend.dto.ApiResponse;
import com.himashana.dkn.dkn_backend.message.model.Message;
import com.himashana.dkn.dkn_backend.message.service.MessageService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<ApiResponse> sendMessage(@RequestBody Message message, Authentication authentication) {
        return messageService.sendMessage(message, authentication);
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getMessage(@PathVariable Long messageId) {
        return messageService.getMessage(messageId);
    }

    @GetMapping
    public ResponseEntity<Iterable<Message>> getAllMessages() {
        return messageService.getAllMessages();
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessage(@PathVariable Long messageId) {
        return messageService.deleteMessage(messageId);
    }
}
