package com.example.student_project.repository;

import com.example.student_project.entity.CourseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseDetailsRepository extends JpaRepository<CourseDetails, Long> {

    @Query("SELECT c FROM CourseDetails c WHERE c.id = :courseId")
    CourseDetails findByCourseId(long courseId);

}
