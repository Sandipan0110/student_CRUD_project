package com.example.student_project.service;

import com.example.student_project.config.OmuniResponse;
import com.example.student_project.dtos.CourseDetailsDto;
import com.example.student_project.dtos.EmployeeDetailsDto;
import com.example.student_project.dtos.StudentDetailsDto;
import com.example.student_project.entity.CourseDetails;
import com.example.student_project.entity.EmployeeDetails;
import com.example.student_project.entity.StudentDetails;
import com.example.student_project.exception.BadRequestException;
import com.example.student_project.repository.CourseDetailsRepository;
import com.example.student_project.repository.EmployeeDetailsRepository;
import com.example.student_project.repository.StudentDetailsRepository;
import com.example.student_project.utils.HelperService;
import com.example.student_project.utils.ValidationUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.student_project.constant.Constants.CACHE_EVICTED_SUCESSFULLY;
import static com.example.student_project.constant.Constants.FETCHING_DATA_FROM_DB;
import static com.example.student_project.constant.Constants.INVALID_COURSE_ID;
import static com.example.student_project.constant.Constants.INVALID_COURSE_ID_OR_NAME;
import static com.example.student_project.constant.Constants.INVALID_EMPLOYEE_ID;
import static com.example.student_project.constant.Constants.INVALID_NAME;
import static com.example.student_project.constant.Constants.NO_STUDENT_PRESENT;
import static com.example.student_project.constant.Constants.STUDENT_DETAILS_DELETED_SUCCESSFULLY;
import static com.example.student_project.constant.Constants.STUDENT_DETAILS_SAVED_SUCCESSFULLY;
import static com.example.student_project.constant.Constants.STUDENT_DETAILS_UPDATED_SUCCESSFULLY;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentDetailsServiceImpl implements StudentDetailsService {

    @Autowired
    CourseDetailsRepository courseDetailsRepository;

    @Autowired
    EmployeeDetailsRepository employeeDetailsRepository;

    @Autowired
    StudentDetailsRepository studentDetailsRepository;

    @Autowired
    HelperService helperService;

    @Override
    public OmuniResponse<String> addStudent(StudentDetailsDto studentDetailsDto) {
        try {
            ValidationUtils.validateStudentDetails(studentDetailsDto);
            StudentDetails studentDetailsEntity = studentDetailsRepository.findByPhoneNumber(studentDetailsDto.getPhoneNumber());
            if (studentDetailsEntity != null) {
                throw new BadRequestException("Exception occurred while adding student details ");
            } else {
                StudentDetails studentDetails = helperService.studentDetailsDtoToEntity(studentDetailsDto);
                studentDetailsRepository.save(studentDetails);
                return OmuniResponse.<String>builder().data(STUDENT_DETAILS_SAVED_SUCCESSFULLY).build();
            }
        } catch (Exception e) {
            log.error("Student is Already Exist ", e);
            return OmuniResponse.<String>builder().error("Student is Already Exist ").build();
        }
    }

    @Override
    @Cacheable(cacheNames = "studentDetails", key = "#courseId")
    public OmuniResponse<List<StudentDetailsDto>> fetchStudentsByCourseId(long courseId) {
        try {
            log.info(FETCHING_DATA_FROM_DB);
            if (ValidationUtils.validateCourseId(courseId)) {
                log.error(INVALID_COURSE_ID);
                throw new BadRequestException(INVALID_COURSE_ID);
            }
            List<StudentDetails> studentDetails = studentDetailsRepository.findStudentsByCourseId(courseId);
            if (studentDetails.isEmpty()) {
                log.error(NO_STUDENT_PRESENT);
                throw new BadRequestException(NO_STUDENT_PRESENT + " courseId " + courseId);
            }
            List<StudentDetailsDto> studentDetailsDto = studentDetails.stream()
                    .map(studentDetail -> helperService.studentsDetailsEntityToDto(studentDetail))
                    .collect(Collectors.toList());
            return OmuniResponse.<List<StudentDetailsDto>>builder().data(studentDetailsDto).build();
        } catch (Exception e) {
            log.error("An unexpected error occurred", e);
            return OmuniResponse.<List<StudentDetailsDto>>builder().error("An unexpected error occurred").build();
        }
    }


    @Override
    @Cacheable(cacheNames = "studentDetails", key = "{#pageNumber, #pageSize}")
    public OmuniResponse<List<StudentDetailsDto>> fetchAllStudents(Integer pageNumber, Integer pageSize) {
        try {
            log.info(FETCHING_DATA_FROM_DB);
            if (pageNumber < 0 || pageSize <= 0) {
                throw new IllegalArgumentException("Invalid page parameters");
            }
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<StudentDetails> studentDetailsPage = studentDetailsRepository.findAll(pageable);
            List<StudentDetails> studentDetailsPageContent = studentDetailsPage.getContent();
            List<StudentDetailsDto> studentsDetailsDtoList = studentDetailsPageContent.stream()
                    .map(studentDetail -> helperService.studentsDetailsEntityToDto(studentDetail))
                    .collect(Collectors.toList());
            return OmuniResponse.<List<StudentDetailsDto>>builder().data(studentsDetailsDtoList).build();
        } catch (Exception e) {
            log.error("An unexpected error occurred", e);
            return OmuniResponse.<List<StudentDetailsDto>>builder().error("An unexpected error occurred").build();
        }
    }

    @Override
    @Cacheable(cacheNames = "studentDetails", key = "#employeeId")
    public OmuniResponse<List<StudentDetailsDto>> fetchStudentsByEmployeeId(long employeeId) {
        try {
            log.info(FETCHING_DATA_FROM_DB);
            if (ValidationUtils.validateEmployeeId(employeeId)) {
                log.error(INVALID_EMPLOYEE_ID);
                throw new BadRequestException(INVALID_EMPLOYEE_ID);
            }
            List<StudentDetails> studentDetails = studentDetailsRepository.findStudentsByEmployeeId(employeeId);
            if (studentDetails.isEmpty()) {
                log.error(NO_STUDENT_PRESENT);
                throw new BadRequestException(NO_STUDENT_PRESENT + " employeeId " + employeeId);
            }
            List<StudentDetailsDto> studentDetailsDto = studentDetails.stream()
                    .map(studentDetail -> helperService.studentsDetailsEntityToDto(studentDetail))
                    .collect(Collectors.toList());
            return OmuniResponse.<List<StudentDetailsDto>>builder().data(studentDetailsDto).build();
        } catch (Exception e) {
            log.error("An unexpected error occurred", e);
            return OmuniResponse.<List<StudentDetailsDto>>builder().error("An unexpected error occurred").build();
        }
    }

    @Override
    @CacheEvict(cacheNames = "studentDetails", allEntries = true)
    public OmuniResponse<String> updateStudentDetails(StudentDetailsDto studentDetailsDto) {
        try {
            if (ValidationUtils.validateStudentName(studentDetailsDto.getName())) {
                log.error(INVALID_NAME);
                throw new BadRequestException(INVALID_NAME);
            } else {
                StudentDetails studentDetailsEntity = studentDetailsRepository.findByPhoneNumber(studentDetailsDto.getPhoneNumber());
                studentDetailsRepository.save(helperService.updatedStudentDetails(studentDetailsEntity, studentDetailsDto));
                return OmuniResponse.<String>builder().data(STUDENT_DETAILS_UPDATED_SUCCESSFULLY).build();
            }
        } catch (Exception e) {
            log.error("Exception occurred while updating student details", e);
            return OmuniResponse.<String>builder().error("An error occurred while processing your request.").build();
        }
    }

    @Override
    public OmuniResponse<String> deleteByNameAndCourseId(String name, long courseId) {
        try {
            if (ValidationUtils.validateStudentName(name) && ValidationUtils.validateCourseId(courseId)) {
                throw new BadRequestException(INVALID_COURSE_ID_OR_NAME);
            }
            StudentDetails studentDetails = studentDetailsRepository.findStudentsByNameAndCourseId(name, courseId);
            if (ObjectUtils.isEmpty(studentDetails)) {
                log.error(NO_STUDENT_PRESENT);
                throw new BadRequestException(NO_STUDENT_PRESENT + "Name : " + name + "CourseId : " + courseId);
            }
            studentDetailsRepository.delete(studentDetails);
            return OmuniResponse.<String>builder().data(STUDENT_DETAILS_DELETED_SUCCESSFULLY).build();
        } catch (Exception e) {
            log.error("An unexpected error occurred", e);
            return OmuniResponse.<String>builder().error("An unexpected error occurred").build();
        }
    }

    @Override
    @Cacheable(cacheNames = "studentDetails", key = "#name")
    public OmuniResponse<StudentDetailsDto> fetchStudentsByName(String name) {
        if (ValidationUtils.validateStudentName(name)) {
            log.error(INVALID_NAME);
            throw new BadRequestException(INVALID_NAME);
        }
        StudentDetails studentDetails = studentDetailsRepository.findByName(name);
        if (studentDetails == null) {
            log.error(NO_STUDENT_PRESENT);
            throw new BadRequestException(NO_STUDENT_PRESENT + "Name : " + name);
        }
        log.info(FETCHING_DATA_FROM_DB);
        StudentDetailsDto studentDetailsDto = helperService.studentsDetailsEntityToDto(studentDetails);
        return OmuniResponse.<StudentDetailsDto>builder().data(studentDetailsDto).build();
    }

    @Override
    @Cacheable(cacheNames = "courseDetails", key = "#courseId")
    public OmuniResponse<CourseDetailsDto> fetchCourseDetails(long courseId) {
        if (ValidationUtils.validateCourseId(courseId)) {
            log.error(INVALID_COURSE_ID);
            throw new BadRequestException(INVALID_COURSE_ID);
        }
        CourseDetails courseDetails = courseDetailsRepository.findByCourseId(courseId);
        if (courseDetails == null) {
            log.error("No Course Found With Given CourseId : " + courseId);
            throw new BadRequestException("No Course Found With Given CourseId : " + courseId);
        }
        CourseDetailsDto courseDetailsDto = helperService.courseDetailEntityToDto(courseDetails);
        log.info(FETCHING_DATA_FROM_DB);
        return OmuniResponse.<CourseDetailsDto>builder().data(courseDetailsDto).build();
    }

    @Override
    @Cacheable(cacheNames = "employeeDetails", key = "#employeeId")
    public OmuniResponse<EmployeeDetailsDto> fetchEmployeeDetails(long employeeId) {
        if (ValidationUtils.validateEmployeeId(employeeId)) {
            log.error(INVALID_EMPLOYEE_ID);
            throw new BadRequestException(INVALID_EMPLOYEE_ID);
        }
        EmployeeDetails employeeDetails = employeeDetailsRepository.findByEmployeeId(employeeId);
        if (employeeDetails == null) {
            log.error("No Employee Found With Given EmployeeId : " + employeeId);
            throw new BadRequestException("No Employee Found With Given EmployeeId : " + employeeId);
        }
        EmployeeDetailsDto employeeDetailsDto = helperService.employeeDetailsEntityToDto(employeeDetails);
        log.info(FETCHING_DATA_FROM_DB);
        return OmuniResponse.<EmployeeDetailsDto>builder().data(employeeDetailsDto).build();
    }

    @Override
    @CacheEvict(cacheNames = "studentDetails", allEntries = true)
    public void evictStudentCache() {
        log.info(CACHE_EVICTED_SUCESSFULLY);
    }

}

