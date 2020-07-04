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

    @Getter
    public enum ApiErrorType {

        INTERNAL_SERVER_ERROR("/internal-server-error", "Internal Server Error"),
        INVALID_PARAMETER("/invalid-parameter", "Invalid Parameter"),
        MESSAGE_NOT_READABLE("/message-not-readable", "Message Not Readable"),
        RESOURCE_NOT_FOUND("/resource-not-found", "Resource Not Found"),
        ENTITY_BEING_USED("/entity-being-used", "Entity Being Used"),
        BUSINESS_EXCEPTION("/business-exception", "Business Exception"),
        INVALID_DATA("/invalid-data", "Invalid Data");

        private String title;
        private String uri;

        ApiErrorType(String path, String title) {
            this.uri = "https://drugo.dev/algafoodapi" + path;
            this.title = title;
        }
    }

}
