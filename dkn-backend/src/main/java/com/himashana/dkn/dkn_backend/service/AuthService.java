package com.himashana.dkn.dkn_backend.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.himashana.dkn.dkn_backend.dto.ApiResponse;
import com.himashana.dkn.dkn_backend.model.AppUser;
import com.himashana.dkn.dkn_backend.repository.AppUserRepository;
import com.himashana.dkn.dkn_backend.requests.LoginRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    AppUserRepository appUserRepository;

    public ResponseEntity<ApiResponse> authenticateUser(LoginRequest LoginRequest){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(LoginRequest.getEmail(), LoginRequest.getPassword()));

        Optional<AppUser> appUser = appUserRepository.findByEmail(LoginRequest.getEmail());

        ApiResponse apiResponse = new ApiResponse("");

        if(appUser.isPresent()){
            var token = jwtService.createToken(new HashMap<>(), appUser.get());
            apiResponse.setResponse("Authenticated successfully.");
            apiResponse.setToken(token);
        }else{
            apiResponse.setResponse("Did not found the user.");
        }

        return ResponseEntity.ok(apiResponse);
    }

}
