package com.ably.demo.errors;

import com.ably.demo.dto.Error;
import com.ably.demo.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
class CustomControllerAdvice {

    @ExceptionHandler(AlreadyExistUserException.class) // exception handled
    public ResponseEntity<ErrorResponse> handleAlreadyExistUserException(
            HttpServletRequest req, Exception e, Model model
    ) {
        HttpStatus status = HttpStatus.CONFLICT; // 409

        return new ResponseEntity<>(
                new ErrorResponse(
                        new Error(ErrorCode.SIGNUP_ALREADY_EXIST_USER, req.getServletPath(), e.getMessage())
                ),
                status
        );
    }

    @ExceptionHandler({SMSNetworkException.class,SMSNumberException.class}) // exception handled
    public ResponseEntity<ErrorResponse> handleSMSNumberException(
            HttpServletRequest req, SMSException e, Model model
    ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        ErrorCode errorCode = null;
        if(e instanceof SMSNetworkException) {
            errorCode = ErrorCode.SMS_NETWORK_ERROR;
        } else if(e instanceof SMSNumberException){
            errorCode = ErrorCode.SMS_INVALID_NUMBER;
        }

        return new ResponseEntity<>(
                new ErrorResponse(
                        new Error(errorCode, req.getServletPath(), e.getMessage())
                ),
                status
        );
    }

    @ExceptionHandler({SignupBadRequestException.class, SignupInvalidAuthCodeException.class, SignupWrongAccessException.class}) // exception handled
    public ResponseEntity<ErrorResponse> handleSignupException(
            HttpServletRequest req, SignupException e, Model model
    ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        ErrorCode errorCode = null;
        if(e instanceof SignupBadRequestException) {
            errorCode = ErrorCode.SIGNUP_BAD_REQUEST;
        } else if(e instanceof SignupInvalidAuthCodeException){
            errorCode = ErrorCode.SIGNUP_INVALID_AUTHCODE;
        } else if(e instanceof SignupWrongAccessException){
            errorCode = ErrorCode.SIGNUP_WRONG_ACCESS;
        }

        return new ResponseEntity<>(
                new ErrorResponse(
                        new Error(errorCode, req.getServletPath(), e.getMessage())
                ),
                status
        );
    }

    // fallback method
    @ExceptionHandler(ConstraintViolationException.class) // exception handled
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            HttpServletRequest req, Exception e, Model model
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 400

        return new ResponseEntity<>(
                new ErrorResponse(
                        new Error(ErrorCode.SIGNUP_BAD_REQUEST, req.getServletPath(), "")
                ),
                status
        );
    }

    // fallback method
    @ExceptionHandler({Exception.class, NullPointerException.class}) // exception handled
    public ResponseEntity<ErrorResponse> handleExceptions(
            HttpServletRequest req, Exception e, Model model
    ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500

        e.printStackTrace();

        return new ResponseEntity<>(
                new ErrorResponse(
                        new Error(ErrorCode.UNKNOWN, req.getServletPath(), "")
                ),
                status
        );
    }
}
