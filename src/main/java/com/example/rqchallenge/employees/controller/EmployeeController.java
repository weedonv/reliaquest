package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.entity.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/employee")
public class EmployeeController implements IEmployeeController {

    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
        List<Employee> employees = employeeService.getAll();

        if (CollectionUtils.isEmpty(employees)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        List<Employee> employees = employeeService.getAllByName(searchString);

        if (CollectionUtils.isEmpty(employees)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        int intId;
        try {
            intId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            log.error("could not parse id given", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return employeeService.get(intId)
                .map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        Integer highestSalary = employeeService.getHighestSalary();

        if (highestSalary == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(highestSalary);
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        List<String> employeeNames = employeeService.getTopTenEarnersNames();

        if (CollectionUtils.isEmpty(employeeNames)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(employeeNames);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
        if (!employeeInput.containsKey("name")
                || !employeeInput.containsKey("salary")
                || !employeeInput.containsKey("age")) {
            log.error("missing required information for creation of employee");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String name = (String) employeeInput.get("name");
        String salary = (String) employeeInput.get("salary");
        String age = (String) employeeInput.get("age");

        if (!StringUtils.hasText(name) || !StringUtils.hasText(salary) || !StringUtils.hasText(age)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            Integer salaryInt = Integer.parseInt(salary);
            Integer ageInt = Integer.parseInt(age);
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(name, salaryInt, ageInt));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        Employee employee = getEmployeeById(id).getBody();

        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        employeeService.delete(employee);
        return ResponseEntity.status(HttpStatus.OK).body("successfully! deleted Record");
    }
}
