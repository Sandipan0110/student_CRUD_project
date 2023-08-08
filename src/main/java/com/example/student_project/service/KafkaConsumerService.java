package com.example.student_project.service;

import com.example.student_project.dtos.StudentDetailsDto;
import com.example.student_project.entity.StudentDetails;
import com.example.student_project.repository.StudentDetailsRepository;
import com.example.student_project.utils.HelperService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaConsumerService {

    @Autowired
    StudentDetailsRepository studentDetailsRepository;

    @Autowired
    HelperService helperService;

    @KafkaListener(topics = "New_Kafka_Topic_String", groupId = "myNewGroup")
    public void consumeStringMessage(String message) {
        log.info(String.format("Received The Message %s ", message));
    }

    @KafkaListener(topics = "New_Kafka_Topic_Json", groupId = "myNewGroup")
    public void consumeJsonMessage(StudentDetailsDto studentDetailsDto) {
        log.info(String.format("Received The Message : %s ", studentDetailsDto.toString()));
        StudentDetails studentDetails = helperService.studentDetailsDtoToEntity(studentDetailsDto);
        studentDetailsRepository.save(studentDetails);
    }

}
