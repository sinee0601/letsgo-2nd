package com.travel.letsgospringboot.user.controller.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandling {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> exception(Exception ex){
        Map<String, Object> result = new HashMap<>();
        result.put("result", "fail");
        result.put("message", "처리 중 오류가 발생했습니다.");
        return ResponseEntity.internalServerError().body(result);
    }
}
