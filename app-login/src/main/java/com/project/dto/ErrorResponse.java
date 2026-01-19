package com.project.dto;

public record ErrorResponse(
        String timestamp,
        String status,
        String message,
        String path
){}
