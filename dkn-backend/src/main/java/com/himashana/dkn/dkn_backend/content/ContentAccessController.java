package com.himashana.dkn.dkn_backend.content;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.himashana.dkn.dkn_backend.content.model.ContentAccess;
import com.himashana.dkn.dkn_backend.content.service.ContentAccessService;
import com.himashana.dkn.dkn_backend.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/content-access")
public class ContentAccessController {
    private final ContentAccessService contentAccessService;
    
     @PostMapping
    public ResponseEntity<ApiResponse> assignContentToUser(@RequestBody ContentAccess contentAccess) {
        return contentAccessService.assignContentToUser(contentAccess);
    }
}
