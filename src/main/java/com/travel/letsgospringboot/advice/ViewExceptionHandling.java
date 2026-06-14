package com.travel.letsgospringboot.advice;

import com.travel.letsgospringboot.exception.AccessDeniedException;
import com.travel.letsgospringboot.exception.PostNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class ViewExceptionHandling {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(AccessDeniedException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/accessDenied";
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String PostNotFoundException(PostNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/pageNotFound";
    }
}
