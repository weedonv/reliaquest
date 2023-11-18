package com.example.rqchallenge.employees.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {

    @Id
    Integer id;
    @JsonProperty("employee_name")
    String name;
    @JsonProperty("employee_salary")
    Integer salary;
    @JsonProperty("employee_age")
    Integer age;
    @JsonProperty("profile_image")
    @Column("PROFILE_IMAGE")
    String profileImage;

}
