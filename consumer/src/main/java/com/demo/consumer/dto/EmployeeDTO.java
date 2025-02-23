package com.demo.consumer.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeDTO {
    private int id;
    private String name;
    private String department;
    private double salary;
}