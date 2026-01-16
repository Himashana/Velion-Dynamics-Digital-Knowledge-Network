package com.himashana.dkn.dkn_backend.content;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.himashana.dkn.dkn_backend.content.model.Content;
import com.himashana.dkn.dkn_backend.content.service.ContentService;
import com.himashana.dkn.dkn_backend.dto.ApiResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contents")
public class ContentController {
    private final ContentService contentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadContent(Authentication authentication, @RequestParam("file") MultipartFile file) throws IOException {
        return contentService.uploadContent(authentication, file, null);
    }

    @PostMapping(value = "/upload/{workspaceId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadContentToWorkspace(Authentication authentication, @RequestParam("file") MultipartFile file, @PathVariable Long workspaceId) throws IOException {
        return contentService.uploadContent(authentication, file, workspaceId);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<Content> getContent(@PathVariable Long contentId) {
        return contentService.getContent(contentId);
    }

    @GetMapping
    public ResponseEntity<Iterable<Content>> getAllContents() {
        return contentService.getAllContents();
    }
    
    @PutMapping
    public ResponseEntity<ApiResponse> updateContent(@RequestBody Content content) {
        return contentService.updateContent(content);
    }

    @DeleteMapping("/{contentId}")
    public ResponseEntity<ApiResponse> deleteContent(@PathVariable Long contentId) {
        return contentService.deleteContent(contentId);
    }
}
