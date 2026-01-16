package com.himashana.dkn.dkn_backend.assistance;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.himashana.dkn.dkn_backend.assistance.model.Assistance;
import com.himashana.dkn.dkn_backend.assistance.requests.AssistanceCallRequest;
import com.himashana.dkn.dkn_backend.assistance.service.AssistanceService;
import com.himashana.dkn.dkn_backend.dto.ApiResponse;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/assistance")
public class AssistanceController {
    private final AssistanceService assistanceService;

    @PostMapping("/schedule")
    public ResponseEntity<ApiResponse> scheduleCall(@RequestBody AssistanceCallRequest assistanceCallRequest, Authentication authentication) {
        return assistanceService.scheduleCall(assistanceCallRequest, authentication);
    }

    @GetMapping("/take/{assistanceId}")
    public ResponseEntity<Assistance> takeCall(@PathVariable Long assistanceId) {
        return assistanceService.takeCall(assistanceId);
    }

    @DeleteMapping("/cancel/{assistanceId}")
    public ResponseEntity<ApiResponse> cancelCall(@PathVariable Long assistanceId) {
        return assistanceService.cancelCall(assistanceId);
    }
}
