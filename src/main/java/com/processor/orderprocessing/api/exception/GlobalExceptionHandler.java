package com.processor.orderprocessing.api.exception;

import com.processor.orderprocessing.api.response.ErrorResponse;
import com.processor.orderprocessing.application.exception.DuplicateRequestException;
import com.processor.orderprocessing.domain.exception.OrderNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /* ============================
       DOMAIN / APPLICATION EXCEPTIONS
       ============================ */

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFound(
            OrderNotFoundException ex,
            HttpServletRequest request
    ) {

        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request,
                null
        );
    }

    @ExceptionHandler(DuplicateRequestException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateRequest(
            DuplicateRequestException ex,
            HttpServletRequest request
    ) {

        return buildErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request,
                null
        );
    }

    /* ============================
       VALIDATION ERRORS
       ============================ */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        List<String> validationErrors =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(this::formatFieldError)
                        .toList();

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                request,
                validationErrors
        );
    }

    /* ============================
       FALLBACK
       ============================ */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {

        log.error("Unexpected error", ex);

        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                request,
                null
        );
    }

    /* ============================
       HELPER
       ============================ */

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status,
            String message,
            HttpServletRequest request,
            List<String> validationErrors
    ) {

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .correlationId(request.getHeader("X-Correlation-Id"))
                .validationErrors(validationErrors)
                .build();

        return ResponseEntity.status(status).body(response);
    }

    private String formatFieldError(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }
}
