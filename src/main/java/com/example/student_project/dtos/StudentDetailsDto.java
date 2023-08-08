package com.example.student_project.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentDetailsDto implements Serializable {

    @NotNull
    String name;

    int age;

    String gender;

    Long phoneNumber;

    int creditPoints;

    String address;

    List<CourseDetailsDto> courseDetailsDto;

}
