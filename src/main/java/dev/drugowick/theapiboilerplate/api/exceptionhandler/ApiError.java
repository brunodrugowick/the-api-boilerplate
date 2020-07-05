package dev.drugowick.theapiboilerplate.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * A class to specify API problems according to RFC 7807 - Problem Details for HTTP APIs.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ApiError {

    private final Integer status;
    private final String type;
    private final String title;
    private final String detail;

    private final String userMessage;
    private final OffsetDateTime timestamp;

    private final List<Object> errorObjects;

    @Getter
    @Builder
    public static class Object {

        private final String name;
        private final String userMessage;
    }
}
