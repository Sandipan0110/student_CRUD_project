package com.example.student_project.utils;

import com.example.student_project.dtos.CourseDetailsDto;
import com.example.student_project.dtos.EmployeeDetailsDto;
import com.example.student_project.dtos.StudentDetailsDto;
import com.example.student_project.entity.CourseDetails;
import com.example.student_project.entity.EmployeeDetails;
import com.example.student_project.entity.StudentDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HelperService {

    public StudentDetails studentDetailsDtoToEntity(StudentDetailsDto studentDetailsDto) {
        if (studentDetailsDto == null){
            return null;
        }
        ValidationUtils.studentValidation(studentDetailsDto);
        StudentDetails studentDetails = new StudentDetails();
        studentDetails.setName(studentDetailsDto.getName());
        studentDetails.setAge(studentDetailsDto.getAge());
        studentDetails.setGender(studentDetailsDto.getGender());
        studentDetails.setPhoneNumber(studentDetailsDto.getPhoneNumber());
        studentDetails.setCreditPoints(studentDetailsDto.getCreditPoints());
        studentDetails.setAddress(studentDetailsDto.getAddress());
        List<CourseDetails> courseDetails = studentDetailsDto.getCourseDetailsDto().stream()
                .map(courseDetailsDto -> courseDetailsDtoToEntity(studentDetails, courseDetailsDto))
                .collect(Collectors.toList());
        studentDetails.setCourseDetails(courseDetails);
        return studentDetails;
    }

    public StudentDetailsDto studentsDetailsEntityToDto(StudentDetails studentDetails) {
        if (studentDetails == null) {
            return null;
        }
        StudentDetailsDto studentDetailsDto = new StudentDetailsDto();
        ValidationUtils.studentValidation(studentDetailsDto);
        studentDetailsDto.setName(studentDetails.getName());
        studentDetailsDto.setAge(studentDetails.getAge());
        studentDetailsDto.setGender(studentDetails.getGender());
        studentDetailsDto.setPhoneNumber(studentDetails.getPhoneNumber());
        studentDetailsDto.setCreditPoints(studentDetails.getCreditPoints());
        studentDetailsDto.setAddress(studentDetails.getAddress());
        List<CourseDetails> courseDetails = studentDetails.getCourseDetails();
        List<CourseDetailsDto> courseDetailsDtos = courseDetails.stream()
                .map(this::courseDetailEntityToDto)
                .collect(Collectors.toList());
        studentDetailsDto.setCourseDetailsDto(courseDetailsDtos);
        return studentDetailsDto;
    }

    public CourseDetails courseDetailsDtoToEntity(StudentDetails studentDetails, CourseDetailsDto courseDetailsDto) {
        if (courseDetailsDto == null){
            return null;
        }
        ValidationUtils.courseValidation(courseDetailsDto);
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setName(courseDetailsDto.getName());
        courseDetails.setDescription(courseDetailsDto.getDescription());
        courseDetails.setMarks(courseDetailsDto.getMarks());
        courseDetails.setStudentDetails(studentDetails);
        courseDetails.setEmployeeDetails(employeeDetailsDtoToEntity(courseDetailsDto.getEmployeeDetailsDto()));
        return courseDetails;
    }

    public CourseDetailsDto courseDetailEntityToDto(CourseDetails courseDetails) {
        if (courseDetails == null) {
            return null;
        }
        CourseDetailsDto courseDetailsDto = new CourseDetailsDto();
        ValidationUtils.courseValidation(courseDetailsDto);
        courseDetailsDto.setName(courseDetails.getName());
        courseDetailsDto.setDescription(courseDetails.getDescription());
        courseDetailsDto.setMarks(courseDetails.getMarks());
        courseDetailsDto.setEmployeeDetailsDto(employeeDetailsEntityToDto(courseDetails.getEmployeeDetails()));
        return courseDetailsDto;
    }

    public EmployeeDetails employeeDetailsDtoToEntity(EmployeeDetailsDto employeeDetailsDto) {
        if (employeeDetailsDto == null){
            return null;
        }
        ValidationUtils.employeeValidation(employeeDetailsDto);
        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.setName(employeeDetailsDto.getName());
        employeeDetails.setDepartment(employeeDetailsDto.getDepartment());
        employeeDetails.setDesignation(employeeDetailsDto.getDesignation());
        return employeeDetails;
    }

    public EmployeeDetailsDto employeeDetailsEntityToDto(EmployeeDetails employeeDetails) {
        if (employeeDetails == null) {
            return null;
        }
        EmployeeDetailsDto employeeDetailsDto = new EmployeeDetailsDto();
        ValidationUtils.employeeValidation(employeeDetailsDto);
        employeeDetailsDto.setName(employeeDetails.getName());
        employeeDetailsDto.setDesignation(employeeDetails.getDesignation());
        employeeDetailsDto.setDepartment(employeeDetails.getDepartment());
        return employeeDetailsDto;

    }

    public StudentDetails updatedStudentDetails(StudentDetails entityStudentDetails, StudentDetailsDto studentDetailsDto) {
        if (entityStudentDetails == null) {
            return null;
        }
        ValidationUtils.studentValidation(studentDetailsDto);
        entityStudentDetails.setAge(studentDetailsDto.getAge());
        entityStudentDetails.setGender(studentDetailsDto.getGender());
        entityStudentDetails.setPhoneNumber(studentDetailsDto.getPhoneNumber());
        entityStudentDetails.setCreditPoints(studentDetailsDto.getCreditPoints());
        entityStudentDetails.setAddress(studentDetailsDto.getAddress());
        List<CourseDetailsDto> courseDetailsDto = studentDetailsDto.getCourseDetailsDto();
        List<CourseDetails> entityCourseDetails = entityStudentDetails.getCourseDetails();
        List<CourseDetails> updatedCourseDetails = new ArrayList<>();
        for (int i = 0; i < entityCourseDetails.size(); i++) {
            updatedCourseDetails.add(updateCourseDetails(entityCourseDetails.get(i), courseDetailsDto.get(i)));
        }
        entityStudentDetails.setCourseDetails(updatedCourseDetails);
        return entityStudentDetails;
    }

    public CourseDetails updateCourseDetails(CourseDetails entityCourseDetails, CourseDetailsDto courseDetailsDto) {
        if (entityCourseDetails == null) {
            return null;
        }
        ValidationUtils.courseValidation(courseDetailsDto);
        entityCourseDetails.setName(courseDetailsDto.getName());
        entityCourseDetails.setDescription(courseDetailsDto.getDescription());
        entityCourseDetails.setMarks(courseDetailsDto.getMarks());
        entityCourseDetails.setEmployeeDetails(updatedEmployeeDetails(entityCourseDetails.getEmployeeDetails(), courseDetailsDto.getEmployeeDetailsDto()));
        return entityCourseDetails;
    }

    public EmployeeDetails updatedEmployeeDetails(EmployeeDetails entityEmployeeDetails, EmployeeDetailsDto employeeDetailsDto) {
        if (entityEmployeeDetails == null) {
            return null;
        }
        ValidationUtils.employeeValidation(employeeDetailsDto);
        entityEmployeeDetails.setName(employeeDetailsDto.getName());
        entityEmployeeDetails.setDesignation(employeeDetailsDto.getDesignation());
        entityEmployeeDetails.setDepartment(employeeDetailsDto.getDepartment());
        return entityEmployeeDetails;
    }

}
