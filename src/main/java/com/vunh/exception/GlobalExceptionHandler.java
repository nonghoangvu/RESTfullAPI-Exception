package com.vunh.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidException(Exception e, WebRequest request) {
        System.out.println("===========handleValidException==========");
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

//        errorResponse.setPath(request.getContextPath());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));

//        errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());

        String message = e.getMessage();
//        int start = message.lastIndexOf("[");
//        int end = message.lastIndexOf("]");
//        message = message.substring(start + 1, end - 1);

        if (e instanceof MethodArgumentNotValidException) {
            int start = message.lastIndexOf("[");
            int end = message.lastIndexOf("]");
            message = message.substring(start + 1, end - 1);
            errorResponse.setError("Payload invalid");
        } else if (e instanceof ConstraintViolationException) {
            message = message.substring(message.indexOf(" ") + 1);
            errorResponse.setError("PathVariable invalid");
        }

        errorResponse.setMessage(message);
        return errorResponse;
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerException(Exception e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));

        if (e instanceof MethodArgumentTypeMismatchException) {
            errorResponse.setError("Payload invalid");
            errorResponse.setMessage("Failed to convert value of type");
        } else if (e instanceof ResourceNotFoundException) {
            errorResponse.setError("Internal Server Error");
            errorResponse.setMessage("Something went wrong");
        }

        return errorResponse;
    }
}
