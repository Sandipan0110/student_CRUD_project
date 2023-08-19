package com.example.student_project.repository;

import com.example.student_project.entity.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface StudentDetailsRepository extends JpaRepository<StudentDetails, Long> {

    StudentDetails findByName(String name);

    StudentDetails findByPhoneNumber(long phoneNumber);

    @Query("SELECT s FROM StudentDetails s JOIN s.courseDetails c WHERE c.id = :courseId")
    List<StudentDetails> findStudentsByCourseId(long courseId);

    @Query("SELECT s FROM StudentDetails s JOIN s.courseDetails c INNER JOIN c.employeeDetails as e WHERE e.id = :employeeId")
    List<StudentDetails> findStudentsByEmployeeId(long employeeId);

    @Query("SELECT s FROM StudentDetails s JOIN s.courseDetails c  WHERE s.name = :name AND c.id = :courseId")
    StudentDetails findStudentsByNameAndCourseId(String name, long courseId);

}
