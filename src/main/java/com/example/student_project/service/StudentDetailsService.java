package com.example.student_project.service;

import com.example.student_project.config.OmuniResponse;
import com.example.student_project.dtos.CourseDetailsDto;
import com.example.student_project.dtos.EmployeeDetailsDto;
import com.example.student_project.dtos.StudentDetailsDto;

import java.util.List;

public interface StudentDetailsService {

    OmuniResponse<String> addStudent(StudentDetailsDto studentDetailsDto);

    OmuniResponse<List<StudentDetailsDto>> fetchStudentsByCourseId(long courseId);

    OmuniResponse<List<StudentDetailsDto>> fetchAllStudents(Integer pageNo, Integer pageSize);

    OmuniResponse<List<StudentDetailsDto>> fetchStudentsByEmployeeId(long employeeId);

    OmuniResponse<String> updateStudentDetails(StudentDetailsDto studentDetailsDto);

    OmuniResponse<String> deleteByNameAndCourseId(String name, long courseId);

    OmuniResponse<StudentDetailsDto> fetchStudentsByName(String name);

    OmuniResponse<CourseDetailsDto> fetchCourseDetails(long courseId);

    OmuniResponse<EmployeeDetailsDto> fetchEmployeeDetails(long employeeId);

    void evictStudentCache();

}
