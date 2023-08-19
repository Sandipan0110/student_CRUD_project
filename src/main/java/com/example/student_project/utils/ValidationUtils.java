package com.example.student_project.utils;

import com.example.student_project.dtos.CourseDetailsDto;
import com.example.student_project.dtos.EmployeeDetailsDto;
import com.example.student_project.dtos.StudentDetailsDto;
import com.example.student_project.entity.StudentDetails;
import com.example.student_project.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import static com.example.student_project.constant.Constants.EMPTY_NAME;
import static com.example.student_project.constant.Constants.RegexConstant.ID_VALIDATION;
import static com.example.student_project.constant.Constants.RegexConstant.NAME_VALIDATION;

@Slf4j
public class ValidationUtils {

    public static void validateStudentDetails(StudentDetailsDto studentDetailsDto) {
        if (ObjectUtils.isEmpty(studentDetailsDto.getCourseDetailsDto()) ||
                ObjectUtils.isEmpty(studentDetailsDto.getCourseDetailsDto().get(0).getEmployeeDetailsDto())) {
            throw new BadRequestException("Course Details or Employee Details Should not Be Empty");
        }
    }

    public static boolean validateCourseId(long courseId) {
        String id = String.valueOf(courseId);
        if (!id.matches(ID_VALIDATION)) {
            throw new BadRequestException("Please Enter valid CourseId");
        }
        return false;
    }

    public static boolean validateEmployeeId(long employeeId) {
        String id = String.valueOf(employeeId);
        if (!id.matches(ID_VALIDATION)) {
            throw new BadRequestException("Please Enter valid CourseId");
        }
        return false;
    }

    public static boolean validateStudentName(String name) {
        if (!name.matches(NAME_VALIDATION)) {
            throw new BadRequestException("Please Enter valid Name");
        }
        return false;
    }

    public static void studentValidation(StudentDetails studentDetails) {
        if (ObjectUtils.isEmpty(studentDetails.getName())) {
            throw new BadRequestException(EMPTY_NAME);
        }
        if (ObjectUtils.isEmpty(studentDetails.getAge())) {
            throw new BadRequestException("Age Should Not Be Empty");
        }
        if (ObjectUtils.isEmpty(studentDetails.getGender())) {
            throw new BadRequestException("Gender Should Not Be Empty");
        }
        if (ObjectUtils.isEmpty(studentDetails.getPhoneNumber())) {
            throw new BadRequestException("PhoneNumber Should Not Be Empty");
        }
        if (ObjectUtils.isEmpty(studentDetails.getCreditPoints())) {
            throw new BadRequestException("CreditPoints Should Not Be Empty");
        }
        if (ObjectUtils.isEmpty(studentDetails.getAddress())) {
            throw new BadRequestException("Address Should Not Be Empty");
        }
    }

    public static void studentsValidation(StudentDetailsDto studentDetailsDto) {
        if (ObjectUtils.isEmpty(studentDetailsDto.getName())) {
            throw new BadRequestException(EMPTY_NAME);
        }
        if (ObjectUtils.isEmpty(studentDetailsDto.getAge())) {
            throw new BadRequestException("Age Should Not Be Empty");
        }
        if (ObjectUtils.isEmpty(studentDetailsDto.getGender())) {
            throw new BadRequestException("Gender Should Not Be Empty");
        }
        if (ObjectUtils.isEmpty(studentDetailsDto.getPhoneNumber())) {
            throw new BadRequestException("PhoneNumber Should Not Be Empty");
        }
        if (ObjectUtils.isEmpty(studentDetailsDto.getCreditPoints())) {
            throw new BadRequestException("CreditPoints Should Not Be Empty");
        }
        if (ObjectUtils.isEmpty(studentDetailsDto.getAddress())) {
            throw new BadRequestException("Address Should Not Be Empty");
        }
    }

    public static void courseValidation(CourseDetailsDto courseDetailsDto) {
        if (ObjectUtils.isEmpty(courseDetailsDto.getName())) {
            throw new BadRequestException(EMPTY_NAME);
        }
        if (ObjectUtils.isEmpty(courseDetailsDto.getDescription())) {
            throw new BadRequestException("Description Should Not Be Empty");
        }
        if (ObjectUtils.isEmpty(courseDetailsDto.getMarks())) {
            throw new BadRequestException("Marks Should Not Be Empty");
        }
    }

    public static void employeeValidation(EmployeeDetailsDto employeeDetailsDto) {
        if (ObjectUtils.isEmpty(employeeDetailsDto.getName())) {
            throw new BadRequestException(EMPTY_NAME);
        }
        if (ObjectUtils.isEmpty(employeeDetailsDto.getDepartment())) {
            throw new BadRequestException("Department Should Not Be Empty");
        }
        if (ObjectUtils.isEmpty(employeeDetailsDto.getDesignation())) {
            throw new BadRequestException("Designation Should Not Be Empty");
        }
    }
}

