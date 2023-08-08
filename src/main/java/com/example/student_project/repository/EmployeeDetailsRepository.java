package com.example.student_project.repository;

import com.example.student_project.entity.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Long> {

    @Query("SELECT e FROM EmployeeDetails e WHERE e.id = :employeeId")
    EmployeeDetails findByEmployeeId(long employeeId);

}
