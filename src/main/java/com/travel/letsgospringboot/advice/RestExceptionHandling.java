package com.travel.letsgospringboot.advice;

import com.travel.letsgospringboot.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandling {

    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("ok", false);
        body.put("result", "fail");
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidInput(InvalidInputException ex) {
        log.error("잘못된 입력값: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DuplicateUserIdException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateUserId(DuplicateUserIdException ex) {
        log.error("중복 값 시도: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex) {
        log.error("사용자 없음: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        log.error("권한 위반 차단: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePostNotFoundException(PostNotFoundException ex) {
        log.error("게시글 없음: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AlreadyReportedException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyReported(AlreadyReportedException ex) {
        log.error("중복 신고: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleReportNotFound(ReportNotFoundException ex) {
        log.error("신고 내역 없음: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PlaceOperationException.class)
    public ResponseEntity<Map<String, Object>> handlePlaceOperation(PlaceOperationException ex) {
        log.error("장소 작업 실패: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(PostOperationException.class)
    public ResponseEntity<Map<String, Object>> handlePostOperation(PostOperationException ex) {
        log.error("게시글 작업 실패: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ReportOperationException.class)
    public ResponseEntity<Map<String, Object>> handleReportOperation(ReportOperationException ex) {
        log.error("신고 처리 실패: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 정적 리소스 미존재(favicon.ico 등)는 실제 오류가 아니므로 404로 조용히 응답
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Void> handleNoResource(NoResourceFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        log.error("API 처리 중 오류", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "잘못된 요청입니다.");
    }
}
