package com.example.student_project.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BadRequestException extends RuntimeException {
    String message;

    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }

}