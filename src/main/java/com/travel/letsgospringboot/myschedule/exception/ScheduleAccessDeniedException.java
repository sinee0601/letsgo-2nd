package com.travel.letsgospringboot.myschedule.exception;


public class ScheduleAccessDeniedException extends RuntimeException {
    public ScheduleAccessDeniedException(String message) {
        super(message);
    }
}
