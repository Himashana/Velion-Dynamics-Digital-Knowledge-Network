package com.himashana.dkn.dkn_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class AppUserController {
    @RequestMapping("/test")
    public String test() {
        return "AppUserController is working!";
    }
}
