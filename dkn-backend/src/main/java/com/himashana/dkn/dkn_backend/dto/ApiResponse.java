package com.himashana.dkn.dkn_backend.dto;

public class ApiResponse {
    private String response;
    private String token;
    private String errorCode;

    public ApiResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Object getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
