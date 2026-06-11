package com.travel.letsgospringboot.advice;

import com.travel.letsgospringboot.myschedule.controller.MyScheduleRestController;
import com.travel.letsgospringboot.myschedule.exception.ScheduleAccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandling {

    @ExceptionHandler(ScheduleAccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(ScheduleAccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        log.error("myschedule API 처리 중 오류", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
    }
}
