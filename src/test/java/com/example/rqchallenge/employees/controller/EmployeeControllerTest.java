package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeControllerTest {

    @Autowired
    EmployeeController employeeController;

    //Cheating a bit and using pre-populated employee from the command line runner
    //for prod h2 would be replaced with an external DB and only used in integration test, loading the data on test start
    @Test
    public void getAllEmployees() throws IOException {
        ResponseEntity<List<Employee>> allEmployees = employeeController.getAllEmployees();

        assertEquals(HttpStatus.OK, allEmployees.getStatusCode());
        assertEquals(12, allEmployees.getBody().size());
    }

    @Test
    public void getEmployeesByNameSearch()  {
        ResponseEntity<List<Employee>> allEmployees = employeeController.getEmployeesByNameSearch("Tom");

        assertEquals(HttpStatus.OK, allEmployees.getStatusCode());
        assertEquals(1, allEmployees.getBody().size());
    }

    @Test
    public void getEmployeesByNameSearch_noResult()  {
        ResponseEntity<List<Employee>> allEmployees = employeeController.getEmployeesByNameSearch("asfdgfsa");

        assertEquals(HttpStatus.NOT_FOUND, allEmployees.getStatusCode());
        assertNull(allEmployees.getBody());
    }

    @Test
    public void getEmployeeById()  {
        ResponseEntity<Employee> employee = employeeController.getEmployeeById("1");

        assertEquals(HttpStatus.OK, employee.getStatusCode());
        assertEquals("mark adams", employee.getBody().getName());
    }

    @Test
    public void getEmployeeById_wrongId()  {
        ResponseEntity<Employee> employee = employeeController.getEmployeeById("20");

        assertEquals(HttpStatus.NOT_FOUND, employee.getStatusCode());
        assertNull(employee.getBody());
    }

    @Test
    public void getEmployeeById_invalidFormat()  {
        ResponseEntity<Employee> employee = employeeController.getEmployeeById("adsad");

        assertEquals(HttpStatus.BAD_REQUEST, employee.getStatusCode());
        assertNull(employee.getBody());
    }

    @Test
    public void getHighestSalaryOfEmployees()  {
        ResponseEntity<Integer> employee = employeeController.getHighestSalaryOfEmployees();

        assertEquals(HttpStatus.OK, employee.getStatusCode());
        assertEquals(1000000, employee.getBody());
    }

    @Test
    public void getTop10HighestEarningEmployeeNames()  {
        ResponseEntity<List<String>> employee = employeeController.getTopTenHighestEarningEmployeeNames();

        assertEquals(HttpStatus.OK, employee.getStatusCode());
        assertEquals(10, employee.getBody().size());
        assertEquals("tom richards", employee.getBody().get(0));
    }

    @Test
    @DirtiesContext
    public void createEmployee() {
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("name", "bob ten");
        requestMap.put("salary", "44010");
        requestMap.put("age", "56");

        ResponseEntity<Employee> employee = employeeController.createEmployee(requestMap);

        assertEquals(HttpStatus.CREATED, employee.getStatusCode());
        assertEquals("bob ten", employee.getBody().getName());
    }

    @Test
    public void createEmployee_invalidNumber() {
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("name", "bob ten");
        requestMap.put("salary", "4dasd10");
        requestMap.put("age", "56");

        ResponseEntity<Employee> employee = employeeController.createEmployee(requestMap);

        assertEquals(HttpStatus.BAD_REQUEST, employee.getStatusCode());
    }

    @Test
    public void createEmployee_missingParam() {
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("name", "bob ten");
        requestMap.put("age", "56");

        ResponseEntity<Employee> employee = employeeController.createEmployee(requestMap);

        assertEquals(HttpStatus.BAD_REQUEST, employee.getStatusCode());
    }

    @Test
    public void createEmployee_missingValue() {
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("name", "bob ten");
        requestMap.put("salary", null);
        requestMap.put("age", "56");

        ResponseEntity<Employee> employee = employeeController.createEmployee(requestMap);

        assertEquals(HttpStatus.BAD_REQUEST, employee.getStatusCode());
    }

    @Test
    @DirtiesContext
    public void deleteEmployee() {

        ResponseEntity<String> response = employeeController.deleteEmployeeById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}