package com.moex.mp.sys.scheduler.mpsysscheduler.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerResolver extends ResponseEntityExceptionHandler {

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Data access error")
    @ExceptionHandler(DataAccessException.class)
    public void DataAccessExceptionHandler(DataAccessException exception){
        // to do logging
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The cron expression is invalid")
    @ExceptionHandler(CronExpressionIsInvalidException.class)
    public void CronExpressionExceptionHandler(CronExpressionIsInvalidException exception){
        // to do logging
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Only the URL or topic of Kafka should be indicated, but in the correct form.")
    @ExceptionHandler(AddressForMessageException.class)
    public void AddressForMessageExceptionHandler(AddressForMessageException exception){
        // to do logging
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The URI is invalid.")
    @ExceptionHandler(UriSyntaxCustomException.class)
    public void UriSyntaxCustomExceptionHandler(UriSyntaxCustomException exception){
        // to do logging
    }
}
