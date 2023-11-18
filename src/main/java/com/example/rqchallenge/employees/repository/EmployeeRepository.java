package com.example.rqchallenge.employees.repository;

import com.example.rqchallenge.employees.entity.Employee;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    @Query("SELECT * FROM EMPLOYEE WHERE NAME LIKE '%' || :name || '%'")
    List<Employee> findAllByName(String name);

    @Query("SELECT MAX(SALARY) FROM EMPLOYEE")
    Integer getHighestSalary();

    @Query("SELECT NAME FROM EMPLOYEE ORDER BY SALARY DESC LIMIT 10")
    List<String> getTopTenEarners();
}
