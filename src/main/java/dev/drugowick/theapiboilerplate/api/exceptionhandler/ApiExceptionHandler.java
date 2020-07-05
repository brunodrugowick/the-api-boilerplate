package dev.drugowick.theapiboilerplate.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import dev.drugowick.theapiboilerplate.domain.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public static final String DEFAULT_USER_MESSAGE =
            "Internal error. Please try again or contact the system administrator.";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handler(EntityNotFoundException exception, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        String detail = exception.getMessage();

        ApiError apiError = createApiErrorBuilder(status, ApiErrorType.RESOURCE_NOT_FOUND, detail)
                .userMessage(exception.getMessage())
                .build();

        return  handleExceptionInternal(exception, apiError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handler(Exception exception, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detail = exception.getMessage();

        exception.printStackTrace();

        return handleExceptionInternal(exception, null, new HttpHeaders(), status, request);
    }

    /**
     * This method overrides default Spring method to handle exceptions and, if the body is null, creates a body with
     * our definition of ApiError.
     *
     * The INTERNAL_SERVER_ERROR makes sense since everything going through this method is not handled by our code. I
     * understand that in this case Spring is handling the exception and we leverage on Spring's code for a lot of
     * stuff, but in this case there's no other way.
     *
     * For example, it's necessary to handle JsonMappingException to make it (and its child exceptions) return another
     * type of exception.
     *
     * @param ex the exception
     * @param body response body
     * @param headers response headers
     * @param status response http status code
     * @param request request that originate this whole mess
     * @return a {@link ResponseEntity} with the object to be returned to the client
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            String detail = ex.getMessage();
            body = createApiErrorBuilder(status, ApiErrorType.INTERNAL_SERVER_ERROR, detail)
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception exception, BindingResult bindingResult,
                                                            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorType apiErrorType = ApiErrorType.INVALID_DATA;
        String detail = "One or more fields are invalid or missing. Please, make sure you're sending the data " +
                "according the API standards and try again.";

        List<ApiError.Object> errorsList = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();
                    if (objectError instanceof FieldError) name = ((FieldError) objectError).getField();

                    return ApiError.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .userMessage(detail)
                .errorObjects(errorsList)
                .build();

        return handleExceptionInternal(exception, apiError, headers, status, request);
    }

    /**
     * A helper method that returns an ApiErrorBuilder with the default values set primary via an ENUM ApiErrorType.
     * <p>
     * This allows for any additional customization/field to be used together with the builder.
     *
     * @param status       the HTTP Status.
     * @param detail       the detailed message for the error.
     * @return an ApiError.ApiErrorBuilder to be further customized with other property values.
     */
    private ApiError.ApiErrorBuilder createApiErrorBuilder(HttpStatus status, ApiErrorType errorType, String detail) {
        return ApiError.builder()
                .status(status.value())
                .title(errorType.getTitle())
                .type(errorType.getUri())
                .detail(detail)
                .userMessage(DEFAULT_USER_MESSAGE)
                .timestamp(OffsetDateTime.now());
    }
}
