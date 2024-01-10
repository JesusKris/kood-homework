package com.kood.homework.translationapi.model;

/**
 * Enumeration representing different types of API responses with associated
 * HTTP status codes.
 *
 * <p>
 * This enum defines constants for common API response types, each coupled with
 * an HTTP status code.
 * It is used to standardize and provide clarity regarding the nature of
 * responses in the API.
 * </p>
 * 
 * <p>
 * <strong>Author:</strong> JesusKris
 * </p>
 */
public enum ResponseType {
    SUCCESS200("SUCCESSS", 200),
    BADREQUEST400("BAD_REQUEST", 400),
    INTERNALSERVERERROR500("INTERNAL_SERVER_ERROR", 500),
    TOOMANYREQUESTS429("TOO_MANY_REQUEST", 429);

    private final String type;
    private final int code;

    /**
     * Constructs a ResponseType with the specified type and HTTP status code.
     *
     * @param type The type or category of the API response.
     * @param code The HTTP status code associated with the API response.
     */
    ResponseType(String type, int code) {
        this.type = type;
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public String getType() {
        return this.type;
    }
}