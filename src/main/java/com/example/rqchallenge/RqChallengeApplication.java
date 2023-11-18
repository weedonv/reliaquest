package com.example.rqchallenge;

import com.example.rqchallenge.employees.entity.Employee;
import com.example.rqchallenge.employees.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RqChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RqChallengeApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(EmployeeRepository employeeRepository) {
        return args -> {
            employeeRepository.save(new Employee(null, "mark adams", 20000, 18, "/images/mark.jpg"));
            employeeRepository.save(new Employee(null, "tom richards", 1000000, 33, "/images/tom.jpg"));
            employeeRepository.save(new Employee(null, "richard lewis", 500000, 23, "/images/rich.jpg"));
            employeeRepository.save(new Employee(null, "bob one", 44001, 56, "/images/bob.jpg"));
            employeeRepository.save(new Employee(null, "bob two", 44002, 56, "/images/bob.jpg"));
            employeeRepository.save(new Employee(null, "bob three", 44003, 56, "/images/bob.jpg"));
            employeeRepository.save(new Employee(null, "bob four", 44004, 56, "/images/bob.jpg"));
            employeeRepository.save(new Employee(null, "bob five", 44005, 56, "/images/bob.jpg"));
            employeeRepository.save(new Employee(null, "bob six", 44006, 56, "/images/bob.jpg"));
            employeeRepository.save(new Employee(null, "bob seven", 44007, 56, "/images/bob.jpg"));
            employeeRepository.save(new Employee(null, "bob eight", 44008, 56, "/images/bob.jpg"));
            employeeRepository.save(new Employee(null, "bob nine", 44009, 56, "/images/bob.jpg"));

        };
    }

}
