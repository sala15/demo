package com.ably.demo.config;

import com.ably.demo.dto.Error;
import com.ably.demo.dto.ErrorResponse;
import com.ably.demo.errors.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        writePrintErrorResponse(request, response, exception);
//        super.onAuthenticationFailure(request, response, exception);
    }

    private void writePrintErrorResponse(HttpServletRequest req, HttpServletResponse response, AuthenticationException exception) {
        try {
            ErrorResponse errorResponse = new ErrorResponse(getExceptionError(req, exception));
            response.getOutputStream().println(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Error getExceptionError(HttpServletRequest req, AuthenticationException exception) {
        Error error = new Error();
        error.setPath(req.getServletPath());

        if (exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException) {
            error.setErrorCode(ErrorCode.INVALID_USER);
        } else if (exception instanceof AccountExpiredException) {
            error.setErrorCode(ErrorCode.ACCOUNT_EXPIRE);
        } else if (exception instanceof CredentialsExpiredException) {
            error.setErrorCode(ErrorCode.PASSWORD_EXPIRE);
        } else if (exception instanceof DisabledException) {
            error.setErrorCode(ErrorCode.DISABLED_USER);
        } else if (exception instanceof LockedException) {
            error.setErrorCode(ErrorCode.LOCKED_USER);
        } else {
            error.setErrorCode(ErrorCode.UNKNOWN);
        }

        return error;
    }
}
