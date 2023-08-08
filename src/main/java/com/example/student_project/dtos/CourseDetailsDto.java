package com.example.student_project.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseDetailsDto implements Serializable {

    String name;

    String description;

    int marks;

    EmployeeDetailsDto employeeDetailsDto;

}
