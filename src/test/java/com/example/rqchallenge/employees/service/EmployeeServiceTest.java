package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.entity.Employee;
import com.example.rqchallenge.employees.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;


    @Test
    public void getAll() {
        employeeService.getAll();

        verify(employeeRepository).findAll();
    }

    @Test
    public void getAllByName() {
        employeeService.getAllByName("  3ron Smith!");

        verify(employeeRepository).findAllByName("ron smith");
    }

    @Test //overkill but just to show how to do it
    public void get() {
        when(employeeRepository.findById(1)).thenReturn(
                        Optional.of(Employee.builder().id(1).name("test").age(23).salary(30000).build())
        );

        Optional<Employee> employee = employeeService.get(1);

        assertEquals("test", employee.get().getName());
    }

    @Test
    public void getHighestSalary() {
        employeeService.getHighestSalary();

        verify(employeeRepository).getHighestSalary();
    }

    @Test
    public void getTopTenEarnersNames() {
        employeeService.getTopTenEarnersNames();

        verify(employeeRepository).getTopTenEarners();
    }

    @Test
    public void createEmployee() {
        employeeService.createEmployee("  3ron Smith!", 12345, 17);

        verify(employeeRepository).save(new Employee(null, "ron smith", 12345, 17, null));
    }

    @Test
    public void createEmployee_invalidAge() {
        assertThrows(IllegalArgumentException.class,
                () -> employeeService.createEmployee("  3ron Smith!", 12345, 15));
    }

}