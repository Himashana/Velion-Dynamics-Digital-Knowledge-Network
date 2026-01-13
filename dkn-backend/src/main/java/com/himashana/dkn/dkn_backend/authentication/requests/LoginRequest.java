package com.himashana.dkn.dkn_backend.authentication.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class LoginRequest {

    private String email;
    private String password;
    
}
