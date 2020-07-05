package dev.drugowick.theapiboilerplate.api.exceptionhandler;

import lombok.Getter;

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
        this.uri = "<url>" + path;
        this.title = title;
    }
}
