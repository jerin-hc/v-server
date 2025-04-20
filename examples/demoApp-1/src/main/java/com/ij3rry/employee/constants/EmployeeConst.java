package com.ij3rry.employee.constants;

import com.ij3rry.employee.dto.Employee;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class EmployeeConst {
    @Getter
    private static final List<Employee> employees = Arrays.asList(
            new Employee(1, "Employee1", "Java", "L2", 25, 900000),
            new Employee(1, "Employee2", "Python", "L2", 27, 1000000),
            new Employee(1, "Employee3", "Java", "L1", 22, 800000),
            new Employee(1, "Employee4", "Go", "L1", 22, 900000),
            new Employee(1, "Employee5", "Java", "L4", 31, 2000000)
    );

}
