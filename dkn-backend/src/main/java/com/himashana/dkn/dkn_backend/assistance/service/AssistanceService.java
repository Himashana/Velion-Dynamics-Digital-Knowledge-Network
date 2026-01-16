package com.himashana.dkn.dkn_backend.assistance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.himashana.dkn.dkn_backend.assistance.model.Assistance;
import com.himashana.dkn.dkn_backend.assistance.repository.AssistanceRepository;
import com.himashana.dkn.dkn_backend.assistance.requests.AssistanceCallRequest;
import com.himashana.dkn.dkn_backend.dto.ApiResponse;
import com.himashana.dkn.dkn_backend.user.dto.UserDto;
import com.himashana.dkn.dkn_backend.user.model.AppUser;
import com.himashana.dkn.dkn_backend.user.repository.AppUserRepository;
import com.himashana.dkn.dkn_backend.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssistanceService {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AssistanceRepository assistanceRepository;

    private final UserService userService;

    // Schedule assistance call
    public ResponseEntity<ApiResponse> scheduleCall(AssistanceCallRequest assistanceCallRequest, Authentication authentication){
        // Fetch current user
        UserDto currentUser = userService.getCurrentUser(authentication);

        AppUser arrangedBy = appUserRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + currentUser.getUserId()));

        AppUser arrangedTo = appUserRepository.findById(assistanceCallRequest.getArrangedTo())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + assistanceCallRequest.getArrangedTo()));

        Assistance newAssistance = new Assistance();

        newAssistance.setTitle(assistanceCallRequest.getTitle());

        // TODO: Generate call URL with third-party service provider (Zoom)
        String generatedMeetingUrl = "https://zoom.us/j/1234567890"; // Sample URL

        newAssistance.setCallUrl(generatedMeetingUrl);

        newAssistance.setArrangedBy(arrangedBy);
        newAssistance.setArrangedTo(arrangedTo);
        newAssistance.setTimestamp(java.time.LocalDateTime.now().toString());

        // Save assistance call
        assistanceRepository.save(newAssistance);
        return ResponseEntity.ok(new ApiResponse("Assistance call scheduled successfully"));
    }

    // Take call
    public ResponseEntity<Assistance> takeCall(Long assistanceId){
        // The assistance call link will be used to redirect to the third-party service provider's meeting URL in frontend
        Assistance assistance = assistanceRepository.findById(assistanceId)
                .orElseThrow(() -> new RuntimeException("Assistance call not found with id: " + assistanceId));
        return ResponseEntity.ok(assistance);
    }

    // Cancel call
    public ResponseEntity<ApiResponse> cancelCall(Long assistanceId){
        Assistance assistance = assistanceRepository.findById(assistanceId)
                .orElseThrow(() -> new RuntimeException("Assistance call not found with id: " + assistanceId));
    
        // TODO: Make the cancellation in the third-party service provider (Zoom)
        assistanceRepository.delete(assistance);
        return ResponseEntity.ok(new ApiResponse("Assistance call cancelled successfully"));
    }
}
