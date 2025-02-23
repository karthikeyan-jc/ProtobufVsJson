package com.demo.producer.controller;

import com.demo.producer.dto.EmployeeDTO;
import com.demo.producer.helper.Helper;
import com.demo.producer.proto.EmployeeProto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    @GetMapping(value = "/employees/json", produces = "application/json")
    public List<EmployeeDTO> getEmployeesJson() {
        return Helper.createDummyEmployees();
    }

    @GetMapping(value = "/employees/proto", produces = "application/x-protobuf")
    public byte[] getEmployeesProtobuf() {
        EmployeeProto.EmployeeList.Builder listBuilder = EmployeeProto.EmployeeList.newBuilder();

        Helper.createDummyEmployees().forEach(employeeDTO ->
                listBuilder.addEmployees(
                        EmployeeProto.Employee.newBuilder()
                                .setId(employeeDTO.getId())
                                .setName(employeeDTO.getName())
                                .setDepartment(employeeDTO.getDepartment())
                                .setSalary(employeeDTO.getSalary())
                                .build()
                )
        );

        return listBuilder.build().toByteArray();
    }

}