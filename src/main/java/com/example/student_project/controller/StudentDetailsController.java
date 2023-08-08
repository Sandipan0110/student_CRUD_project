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
@RequestMapping(value = "/api/student")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentDetailsController {

    @Autowired
    StudentDetailsService studentDetailsService;

    @PostMapping(value = "/createStudent")
    public OmuniResponse<String> addStudentDetails(@RequestBody StudentDetailsDto studentDetailsDto) {
        return studentDetailsService.saveStudent(studentDetailsDto);
    }

    @GetMapping(value = "/fetchByCourseId/{courseId}")
    public OmuniResponse<List<StudentDetailsDto>> studentsByCourseId(@PathVariable long courseId) {
        return studentDetailsService.getStudentsByCourseId(courseId);
    }

    @GetMapping(value = "/fetchByPagination")
    public OmuniResponse<List<StudentDetailsDto>> fetchAllStudents(@RequestParam(value = "pageNumber", defaultValue = "10", required = false)
                                                                   @Min(value = 0) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = "1", required = false)
                                                                   @Min(value = 1)
                                                                   @Max(value = 50) Integer pageSize) {
        return studentDetailsService.fetchAllStudents(pageNumber, pageSize);
    }

    @GetMapping(value = "/fetchByEmployeeId/{employeeId}")
    public OmuniResponse<List<StudentDetailsDto>> getStudentsDetailsByEmployeeId(@PathVariable long employeeId) {
        return studentDetailsService.getStudentsByEmployeeId(employeeId);
    }

    @PutMapping(value = "/updateStudentDetails")
    public OmuniResponse<String> updateStudentDetailsByName(@Validated @RequestBody StudentDetailsDto studentDetailsDto) {
        return studentDetailsService.updateStudentDetails(studentDetailsDto);
    }

    @DeleteMapping(value = "/deleteByNameAndCourseId/{name}/{courseId}")
    public OmuniResponse<String> deleteStudentByNameAndCourseId(@PathVariable String name, @PathVariable long courseId) {
        return studentDetailsService.deleteByNameAndCourseId(name, courseId);
    }

    @GetMapping(value = "/cacheByName/{name}")
    public OmuniResponse<StudentDetailsDto> getStudentsByName(@PathVariable String name) {
        return studentDetailsService.getStudentsByName(name);
    }

    @GetMapping(value = "/cacheByCourseId/{courseId}")
    public OmuniResponse<CourseDetailsDto> getByCourseById(@PathVariable long courseId) {
        return studentDetailsService.getCourseDetails(courseId);
    }

    @GetMapping(value = "/cacheByEmployeeId/{employeeId}")
    public OmuniResponse<EmployeeDetailsDto> getByEmployeeById(@PathVariable long employeeId) {
        return studentDetailsService.getEmployeeDetails(employeeId);
    }

    @DeleteMapping(value = "/evictCache/StudentDetails")
    public void evictAllStudentDetailsInCache() {
        studentDetailsService.evictStudentCache();
    }

}
