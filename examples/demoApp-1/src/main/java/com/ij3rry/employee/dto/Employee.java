package com.ij3rry.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
    private int id;
    private String name;
    private String skill;
    private String level;
    private int age;
    private int salary;
}
