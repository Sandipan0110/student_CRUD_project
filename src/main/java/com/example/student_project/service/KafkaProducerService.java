package com.example.student_project.service;

import com.example.student_project.dtos.StudentDetailsDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaProducerService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    KafkaTemplate<String, StudentDetailsDto> kafkaTemplateWithStudent;

    public void sendStringMessage(String message) {
        log.info(String.format("Send The Message : %s ", message));
        kafkaTemplate.send("New_Kafka_Topic_String", message);
    }

    public void sendJsonMessage(StudentDetailsDto studentDetailsDto) {
        log.info(String.format("Send The Message : %s ", studentDetailsDto.toString()));
        Message message = MessageBuilder
                .withPayload(studentDetailsDto)
                .setHeader(KafkaHeaders.TOPIC, "New_Kafka_Topic_Json")
                .build();
        kafkaTemplateWithStudent.send(message);
    }

}
