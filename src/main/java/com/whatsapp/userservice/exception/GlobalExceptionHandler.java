package com.whatsapp.userservice.exception;

import com.whatsapp.userservice.constant.ApiConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFound(UserNotFoundException ex) {
        log.error("User not found: {}", ex.getMessage());
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        detail.setType(URI.create(ApiConstants.PROBLEM_DETAIL_TYPE));
        detail.setTitle(ApiConstants.PROBLEM_DETAIL_TITLE_NOT_FOUND);
        return detail;
    }

    @ExceptionHandler(DuplicatePhoneException.class)
    public ProblemDetail handleDuplicatePhone(DuplicatePhoneException ex) {
        log.error("Duplicate phone: {}", ex.getMessage());
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        detail.setType(URI.create(ApiConstants.PROBLEM_DETAIL_TYPE));
        detail.setTitle(ApiConstants.PROBLEM_DETAIL_TITLE_CONFLICT);
        return detail;
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ProblemDetail handleDuplicateUsername(DuplicateUsernameException ex) {
        log.error("Duplicate username: {}", ex.getMessage());
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        detail.setType(URI.create(ApiConstants.PROBLEM_DETAIL_TYPE));
        detail.setTitle(ApiConstants.PROBLEM_DETAIL_TITLE_CONFLICT);
        return detail;
    }

    @ExceptionHandler(ContactAlreadyExistsException.class)
    public ProblemDetail handleContactAlreadyExists(ContactAlreadyExistsException ex) {
        log.error("Contact already exists: {}", ex.getMessage());
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        detail.setType(URI.create(ApiConstants.PROBLEM_DETAIL_TYPE));
        detail.setTitle(ApiConstants.PROBLEM_DETAIL_TITLE_CONFLICT);
        return detail;
    }

    @ExceptionHandler(BlockedUserException.class)
    public ProblemDetail handleBlockedUser(BlockedUserException ex) {
        log.error("Blocked user error: {}", ex.getMessage());
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        detail.setType(URI.create(ApiConstants.PROBLEM_DETAIL_TYPE));
        detail.setTitle(ApiConstants.PROBLEM_DETAIL_TITLE_CONFLICT);
        return detail;
    }

    @ExceptionHandler(ValidationException.class)
    public ProblemDetail handleValidation(ValidationException ex) {
        log.error("Validation error: {}", ex.getErrors());
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        detail.setType(URI.create(ApiConstants.PROBLEM_DETAIL_TYPE));
        detail.setTitle(ApiConstants.PROBLEM_DETAIL_TITLE_VALIDATION);
        detail.setProperty("errors", ex.getErrors());
        return detail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        log.error("Validation failed: {}", errors);
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");
        detail.setType(URI.create(ApiConstants.PROBLEM_DETAIL_TYPE));
        detail.setTitle(ApiConstants.PROBLEM_DETAIL_TITLE_VALIDATION);
        detail.setProperty("errors", errors);
        return detail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        detail.setType(URI.create(ApiConstants.PROBLEM_DETAIL_TYPE));
        detail.setTitle("Internal Server Error");
        return detail;
    }
}
