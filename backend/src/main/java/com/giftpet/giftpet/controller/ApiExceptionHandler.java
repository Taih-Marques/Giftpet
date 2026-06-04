package com.giftpet.giftpet.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<Map<String, String>> handleMissingServletRequestPart(MissingServletRequestPartException ex) {
        String message = switch (ex.getRequestPartName()) {
            case "event" -> "Os dados do evento são obrigatórios.";
            case "images" -> "Envie pelo menos uma imagem do evento.";
            default -> "Parte obrigatória ausente: " + ex.getRequestPartName();
        };

        return ResponseEntity.badRequest().body(Map.of("message", message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
    }
}
