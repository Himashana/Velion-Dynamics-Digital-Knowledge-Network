package com.himashana.dkn.dkn_backend;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@ToString

@Component
public class AppProperties {

    @Value("${spring.application.name}")
    private String appName;
}
