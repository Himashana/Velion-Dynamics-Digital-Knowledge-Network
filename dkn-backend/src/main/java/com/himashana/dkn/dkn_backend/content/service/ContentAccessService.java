package com.himashana.dkn.dkn_backend.content.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.himashana.dkn.dkn_backend.content.model.Content;
import com.himashana.dkn.dkn_backend.content.model.ContentAccess;
import com.himashana.dkn.dkn_backend.content.model.ContentAccessId;
import com.himashana.dkn.dkn_backend.content.repository.ContentAccessRepository;
import com.himashana.dkn.dkn_backend.content.repository.ContentRepository;
import com.himashana.dkn.dkn_backend.dto.ApiResponse;
import com.himashana.dkn.dkn_backend.user.model.AppUser;
import com.himashana.dkn.dkn_backend.user.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentAccessService {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    ContentAccessRepository contentAccessRepository;

    // Assign content to user
    public ResponseEntity<ApiResponse> assignContentToUser(ContentAccess contentAccess){
        Content content = contentRepository.findById(contentAccess.getId().getContentId()).orElse(null);
        if(content == null){
            return ResponseEntity.ok(new ApiResponse("Content not found"));
        }

        AppUser user = appUserRepository.findById(contentAccess.getId().getUserId()).orElse(null);
        if(user == null){
            return ResponseEntity.ok(new ApiResponse("User not found"));
        }

        ContentAccess newContentAccess = new ContentAccess();
        newContentAccess.setId(new ContentAccessId(content.getContentId(), user.getUserId()));
        newContentAccess.setContent(content);
        newContentAccess.setUser(user);
        newContentAccess.setPermissionLevel(contentAccess.getPermissionLevel());
        newContentAccess.setInvitedDate(java.time.LocalDate.now().toString());
        newContentAccess.setInvitedBy(contentAccess.getInvitedBy());

        contentAccessRepository.save(newContentAccess);
        return ResponseEntity.ok(new ApiResponse("Content assigned to user successfully") );
    }
}
