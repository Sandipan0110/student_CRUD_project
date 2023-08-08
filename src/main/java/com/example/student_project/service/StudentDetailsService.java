package com.example.student_project.service;

import com.example.student_project.config.OmuniResponse;
import com.example.student_project.dtos.CourseDetailsDto;
import com.example.student_project.dtos.EmployeeDetailsDto;
import com.example.student_project.dtos.StudentDetailsDto;

import java.util.List;

public interface StudentDetailsService {

    public OmuniResponse<String> saveStudent(StudentDetailsDto studentDetailsDto);

    public OmuniResponse<List<StudentDetailsDto>> getStudentsByCourseId(long courseId);

    public OmuniResponse<List<StudentDetailsDto>> fetchAllStudents(Integer pageNo, Integer pageSize);

    public OmuniResponse<List<StudentDetailsDto>> getStudentsByEmployeeId(long employeeId);

    public OmuniResponse<String> updateStudentDetails(StudentDetailsDto studentDetailsDto);

    public OmuniResponse<String> deleteByNameAndCourseId(String name, long courseId);

    public OmuniResponse<StudentDetailsDto> getStudentsByName(String name);

    public OmuniResponse<CourseDetailsDto> getCourseDetails(long courseId);

    public OmuniResponse<EmployeeDetailsDto> getEmployeeDetails(long employeeId);

    public void evictStudentCache();

}
