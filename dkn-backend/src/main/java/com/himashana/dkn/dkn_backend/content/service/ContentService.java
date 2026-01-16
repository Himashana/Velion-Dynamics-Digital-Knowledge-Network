package com.himashana.dkn.dkn_backend.content.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.himashana.dkn.dkn_backend.content.model.Content;
import com.himashana.dkn.dkn_backend.content.model.ContentAccess;
import com.himashana.dkn.dkn_backend.content.model.ContentAccessId;
import com.himashana.dkn.dkn_backend.content.repository.ContentAccessRepository;
import com.himashana.dkn.dkn_backend.content.repository.ContentRepository;
import com.himashana.dkn.dkn_backend.dto.ApiResponse;
import com.himashana.dkn.dkn_backend.user.dto.UserDto;
import com.himashana.dkn.dkn_backend.user.model.AppUser;
import com.himashana.dkn.dkn_backend.user.repository.AppUserRepository;
import com.himashana.dkn.dkn_backend.user.service.UserService;
import com.himashana.dkn.dkn_backend.workspace.repository.DigitalWorkspaceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentService {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    DigitalWorkspaceRepository digitalWorkspaceRepository;

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    ContentAccessRepository contentAccessRepository;

    private final UserService userService;

    // Allowed content types
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        "application/vnd.ms-excel",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "application/vnd.ms-powerpoint",
        "application/vnd.openxmlformats-officedocument.presentationml.presentation",
        "text/plain",
        "application/pdf"
    );

    // Upload directory
    private static final Path UPLOAD_DIR = Paths.get("uploads");

    // Upload content
    public ResponseEntity<ApiResponse> uploadContent(Authentication authentication, MultipartFile file, Long workspaceId) throws IOException {
        // Fetch current user
        UserDto currentUser = userService.getCurrentUser(authentication);

        ApiResponse apiResponse = new ApiResponse("");

        // If file is empty
        if (file.isEmpty()) {
            apiResponse.setResponse("File is empty");
            return ResponseEntity.badRequest().body(apiResponse);
        }

        // If file type is not allowed
        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            apiResponse.setResponse("File type not allowed");
            return ResponseEntity
                    .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body(apiResponse);
        }

        // Create upload directory if not exists
        Files.createDirectories(UPLOAD_DIR);

        String fileName = file.getOriginalFilename();

        String baseName;
        String extension;

        // Extract base name and extension from filename
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            baseName = fileName.substring(0, dotIndex);
            extension = fileName.substring(dotIndex);
        } else { // If no extension
            baseName = fileName;
            extension = "";
        }

        String finalizedFileName = fileName;
        Path destination = UPLOAD_DIR.resolve(finalizedFileName);

        int index = 1;

        // Increment index of the filename if already exists
        while (Files.exists(destination)) {
            finalizedFileName = baseName + " (" + index + ")" + extension;
            destination = UPLOAD_DIR.resolve(finalizedFileName);
            index++;
        }

        // Save the file
        Files.copy(file.getInputStream(), destination);

        // Save content metadata to database
        Content content = new Content();
        content.setFilePath(destination.toString());
        // Set workspace if provided
        if (workspaceId != null) {
            digitalWorkspaceRepository.findById(workspaceId).ifPresent(content::setDigitalWorkspace);
        }
        content.setFlagged(false);
        content.setDeleted(false);
        Content savedContent = contentRepository.save(content);

        // Assign the write permission to the author
        ContentAccess contentAccess = new ContentAccess();

        AppUser user = appUserRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + currentUser.getUserId()));

        contentAccess.setId(new ContentAccessId(user.getUserId(), savedContent.getContentId()));
        contentAccess.setContent(savedContent);
        contentAccess.setUser(user);
        contentAccess.setPermissionLevel(2); // Write permission
        contentAccess.setInvitedDate(java.time.LocalDate.now().toString());
        contentAccess.setInvitedBy(currentUser.getUserId());

        // Save content access
        contentAccessRepository.save(contentAccess);

        apiResponse.setResponse("File uploaded successfully");
        return ResponseEntity.ok(apiResponse);
    }

    // Retrieve content by ID
    public ResponseEntity<Content> getContent(Long contentId) {
        Content content = contentRepository.findById(contentId)
                .filter(c -> !c.isDeleted())
                .orElseThrow(() -> new RuntimeException("Content not found with id: " + contentId));
        return ResponseEntity.ok(content);
    }

    // Retrieve all contents
    public ResponseEntity<Iterable<Content>> getAllContents() {
        Iterable<Content> contents = contentRepository.findAllByIsDeletedFalse();
        return ResponseEntity.ok(contents);
    }

    // Update an existing content
    public ResponseEntity<ApiResponse> updateContent(Content content) {
        ApiResponse apiResponse = new ApiResponse("");

        Content existingContent = contentRepository.findById(content.getContentId())
                .orElseThrow(() -> new RuntimeException("Content not found with id: " + content.getContentId()));

        // Currently, only tags can be updated
        existingContent.setTags(content.getTags());

        contentRepository.save(existingContent);
        apiResponse.setResponse("Content updated successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    // Soft delete a content
    public ResponseEntity<ApiResponse> deleteContent(Long contentId) {
        ApiResponse apiResponse = new ApiResponse("");

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Content not found with id: " + contentId));

        content.setDeleted(true);
        contentRepository.save(content);

        apiResponse.setResponse("Content deleted successfully.");
        return ResponseEntity.ok(apiResponse);
    }
}
