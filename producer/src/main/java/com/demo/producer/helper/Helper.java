package com.demo.producer.helper;

import com.demo.producer.dto.EmployeeDTO;

import java.util.ArrayList;
import java.util.List;

public class Helper {
    public static List<EmployeeDTO> createDummyEmployees() {
        List<EmployeeDTO> employeeList = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            employeeList.add(EmployeeDTO.builder()
                    .id(i)
                    .name("Employee " + i)
                    .department("Department " + ((i % 5) + 1))
                    .salary(20000 + i)
                    .build());
        }
        return employeeList;
    }
}