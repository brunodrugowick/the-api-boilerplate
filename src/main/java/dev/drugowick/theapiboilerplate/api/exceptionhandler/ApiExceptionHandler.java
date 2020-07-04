package dev.drugowick.theapiboilerplate.api.exceptionhandler;

import dev.drugowick.theapiboilerplate.domain.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String DEFAULT_USER_MESSAGE =
            "Internal error. Please try again or contact the system administrator.";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handler(Exception exception, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detail = exception.getMessage();

        exception.printStackTrace();

        ApiError apiError = createApiErrorBuilder(status, ApiError.ApiErrorType.INTERNAL_SERVER_ERROR, detail)
                .build();

        return handleExceptionInternal(exception, apiError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handler(EntityNotFoundException exception, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        String detail = exception.getMessage();

        ApiError apiError = createApiErrorBuilder(status, ApiError.ApiErrorType.RESOURCE_NOT_FOUND, detail)
                .userMessage(exception.getMessage())
                .build();

        return  handleExceptionInternal(exception, apiError, new HttpHeaders(), status, request);
    }

    /**
     * A helper method that returns an ApiErrorBuilder with the default values set primary via an ENUM ApiErrorType.
     * <p>
     * This allows for any additional customization/field to be used together with the builder.
     *
     * @param status       the HTTP Status.
     * @param apiErrorType the ENUM with the error type information (defines type and title).
     * @param detail       the detailed message for the error.
     * @return an ApiError.ApiErrorBuilder to be further customized with other property values.
     */
    private ApiError.ApiErrorBuilder createApiErrorBuilder(HttpStatus status, ApiError.ApiErrorType apiErrorType, String detail) {
        return ApiError.builder()
                .status(status.value())
                .type(apiErrorType.getUri())
                .title(apiErrorType.getTitle())
                .detail(detail)
                .userMessage(DEFAULT_USER_MESSAGE)
                .timestamp(OffsetDateTime.now());
    }
}
