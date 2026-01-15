package com.himashana.dkn.dkn_backend.content.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.himashana.dkn.dkn_backend.content.repository.ContentRepository;
import com.himashana.dkn.dkn_backend.dto.ApiResponse;
import com.himashana.dkn.dkn_backend.user.repository.AppUserRepository;
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
    public ResponseEntity<ApiResponse> uploadContent(Authentication authentication, MultipartFile file) throws IOException {
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

        apiResponse.setResponse("File uploaded successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
