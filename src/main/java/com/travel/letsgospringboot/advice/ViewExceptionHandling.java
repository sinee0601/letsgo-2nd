package com.travel.letsgospringboot.advice;

import com.travel.letsgospringboot.myschedule.controller.MyScheduleController;
import com.travel.letsgospringboot.myschedule.exception.ScheduleAccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ViewExceptionHandling {

    @ExceptionHandler(ScheduleAccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(ScheduleAccessDeniedException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/accessDenied";
    }
}
