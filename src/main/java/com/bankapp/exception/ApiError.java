package com.bankapp.exception;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiError {
    private LocalDateTime localDateTime;
    private int status;
    private String error;
    private String message;
    private Map<String, String> fieldErrors;
}
