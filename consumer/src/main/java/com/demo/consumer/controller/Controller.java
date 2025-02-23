package com.demo.consumer.controller;

import com.demo.consumer.dto.EmployeeDTO;
import com.demo.consumer.proto.EmployeeProto;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String PRODUCER_URL = "http://localhost:8080/employees/";

    @GetMapping(value = "/test/json")
    public String testJson() {
        long startTime = System.nanoTime();

        ResponseEntity<EmployeeDTO[]> response = restTemplate.getForEntity(PRODUCER_URL +"json", EmployeeDTO[].class);
        EmployeeDTO[] employees = response.getBody();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        return String.format("Time taken to get and parse %d employees in JSON format: %d ms", employees.length, duration);
    }

    @GetMapping(value = "/test/proto")
    public String testProto() throws InvalidProtocolBufferException {
        long startTime = System.nanoTime();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/x-protobuf");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(PRODUCER_URL +"proto",
                HttpMethod.GET, entity, byte[].class);
        byte[] employees = response.getBody();
        EmployeeProto.EmployeeList employeeList = EmployeeProto.EmployeeList.parseFrom(employees);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        return String.format("Time taken to get and parse %d employees in protobuf format: %d ms", employeeList.getEmployeesCount(), duration);
    }
}

