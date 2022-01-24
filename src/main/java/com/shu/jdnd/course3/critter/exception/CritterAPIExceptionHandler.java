package com.shu.jdnd.course3.critter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class CritterAPIExceptionHandler {

    @ExceptionHandler(value = {CritterAPIRequestException.class})
    public ResponseEntity<Object> handleAPIRequestException(CritterAPIRequestException e){

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        APIException apiException = new APIException(
            e.getMessage(),
            HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return  new ResponseEntity<>(apiException, badRequest);
    }
}
