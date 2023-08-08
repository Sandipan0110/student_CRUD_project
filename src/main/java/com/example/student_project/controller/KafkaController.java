package com.example.student_project.controller;

import com.example.student_project.dtos.StudentDetailsDto;
import com.example.student_project.service.KafkaProducerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
@AllArgsConstructor
public class KafkaController {

    @Autowired
    KafkaProducerService kafkaProducerService;

    @GetMapping("/publish/string")
    public ResponseEntity<String> publishStringMessage(@RequestParam("message") String message) {
        kafkaProducerService.sendStringMessage(message);
        return ResponseEntity.ok("Successfully Send The String Message ");
    }

    @PostMapping("/publish/json")
    public ResponseEntity<String> publishJsonMessage(@RequestBody StudentDetailsDto studentDetailsDto) {
        kafkaProducerService.sendJsonMessage(studentDetailsDto);
        return ResponseEntity.ok("Successfully Send The Json Message ");
    }

}
