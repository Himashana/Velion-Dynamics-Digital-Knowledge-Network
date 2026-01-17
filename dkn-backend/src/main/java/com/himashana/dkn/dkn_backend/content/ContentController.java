package com.himashana.dkn.dkn_backend.content;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.himashana.dkn.dkn_backend.content.model.Content;
import com.himashana.dkn.dkn_backend.content.service.ContentService;
import com.himashana.dkn.dkn_backend.dto.ApiResponse;

import lombok.RequiredArgsConstructor;



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

    @GetMapping("/{contentId}/preview")
    public ResponseEntity<Resource> getContentPreview(@PathVariable Long contentId) throws java.net.MalformedURLException {
        return contentService.getContentPreview(contentId);
    }

    @GetMapping
    public ResponseEntity<Iterable<Content>> getAllContents() {
        return contentService.getAllContents();
    }

    @GetMapping("/workspace/{workspaceId}")
    public ResponseEntity<Iterable<Content>> getContentsByWorkspaceId(@PathVariable Long workspaceId) {
        return contentService.getContentsByWorkspaceId(workspaceId);
    }
    
    @PutMapping
    public ResponseEntity<ApiResponse> updateContent(@RequestBody Content content) {
        return contentService.updateContent(content);
    }

    @PutMapping("/{contentId}/flag")
    public ResponseEntity<ApiResponse> flagContent(@PathVariable Long contentId) {
        return contentService.flagContent(contentId);
    }

    @DeleteMapping("/{contentId}")
    public ResponseEntity<ApiResponse> removeContent(@PathVariable Long contentId) {
        return contentService.removeContent(contentId);
    }
}
