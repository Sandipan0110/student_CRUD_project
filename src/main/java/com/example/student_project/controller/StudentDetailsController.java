package com.example.student_project.controller;

import com.example.student_project.config.OmuniResponse;
import com.example.student_project.dtos.CourseDetailsDto;
import com.example.student_project.dtos.EmployeeDetailsDto;
import com.example.student_project.dtos.StudentDetailsDto;
import com.example.student_project.service.StudentDetailsService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/student")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentDetailsController {

    @Autowired
    StudentDetailsService studentDetailsService;

    @PostMapping("/student")
    public OmuniResponse<String> addStudent(@RequestBody StudentDetailsDto studentDetailsDto) {
        return studentDetailsService.addStudent(studentDetailsDto);
    }

    @GetMapping("/studentByCourseId/{courseId}")
    public OmuniResponse<List<StudentDetailsDto>> studentsByCourseId(@PathVariable long courseId) {
        return studentDetailsService.fetchStudentsByCourseId(courseId);
    }

    @GetMapping("/studentByPagination")
    public OmuniResponse<List<StudentDetailsDto>> fetchAllStudents(@RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                                   @RequestParam(value = "pageSize", defaultValue = "10") @Min(1) @Max(50) Integer pageSize) {
        return studentDetailsService.fetchAllStudents(pageNumber, pageSize);
    }

    @GetMapping("/studentByEmployeeId/{employeeId}")
    public OmuniResponse<List<StudentDetailsDto>> getStudentsDetailsByEmployeeId(@PathVariable long employeeId) {
        return studentDetailsService.fetchStudentsByEmployeeId(employeeId);
    }

    @PutMapping("/student")
    public OmuniResponse<String> updateStudentDetailsByName(@Validated @RequestBody StudentDetailsDto studentDetailsDto) {
        return studentDetailsService.updateStudentDetails(studentDetailsDto);
    }

    @DeleteMapping("/studentByNameAndCourseId/{name}/{courseId}")
    public OmuniResponse<String> deleteStudentByNameAndCourseId(@PathVariable String name, @PathVariable long courseId) {
        return studentDetailsService.deleteByNameAndCourseId(name, courseId);
    }

    @GetMapping("/cacheByName/{name}")
    public OmuniResponse<StudentDetailsDto> getStudentsByName(@PathVariable String name) {
        return studentDetailsService.fetchStudentsByName(name);
    }

    @GetMapping("/cacheByCourseId/{courseId}")
    public OmuniResponse<CourseDetailsDto> getByCourseById(@PathVariable long courseId) {
        return studentDetailsService.fetchCourseDetails(courseId);
    }

    @GetMapping("/cacheByEmployeeId/{employeeId}")
    public OmuniResponse<EmployeeDetailsDto> getByEmployeeById(@PathVariable long employeeId) {
        return studentDetailsService.fetchEmployeeDetails(employeeId);
    }

    @DeleteMapping("/evictCache/student")
    public void evictAllStudentDetailsInCache() {
        studentDetailsService.evictStudentCache();
    }

}
