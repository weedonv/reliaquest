package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.entity.Employee;
import com.example.rqchallenge.employees.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> getAll() {
        return (List<Employee>) employeeRepository.findAll();
    }

    public List<Employee> getAllByName(String name) {
        name = name.trim().toLowerCase().replaceAll("[^a-z\\s]", "");
        return employeeRepository.findAllByName(name);
    }

    public Optional<Employee> get(int id) {
        return employeeRepository.findById(id);
    }

    public Integer getHighestSalary() {
        return employeeRepository.getHighestSalary();
    }

    public List<String> getTopTenEarnersNames() {
        return employeeRepository.getTopTenEarners();
    }

    public Employee createEmployee(String name, Integer salary, Integer age) throws IllegalArgumentException { //TODO create own checked exception
        name = name.trim().toLowerCase().replaceAll("[^a-z\\s]", "");
        if (salary < 0 || age < 16){
            throw new IllegalArgumentException("salary can't be less than 0, ages can't be less than 16");
        }
        return employeeRepository.save(new Employee(null, name, salary, age, null));
    }

    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }
}
