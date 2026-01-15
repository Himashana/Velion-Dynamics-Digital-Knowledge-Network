package com.himashana.dkn.dkn_backend.user.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class UserDto {
    private Long userId;
    private String name;
    private String address;
    private String email;
    private String contactNumber;
    private Integer permissionLevel;
    private String designation;
    private String officeLocation;
    private Map<String, Object> performanceMetrics;
    private String responsibility;
    private List<String> expertDomains;
    private Integer yearsOfExperience;
    private BigDecimal sessionHoursDelivered;
}
