package com.kood.homework.translationapi.model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Represents an error response in the API.
 * <p>
 * This class is designed to encapsulate details of an error response, including
 * error code,
 * type, details, timestamp, path, and API version. It is intended for
 * serialization to JSON
 * and implements the {@link ApiResponse} interface.
 * </p>
 * 
 * <p>
 * <strong>Author:</strong> JesusKris
 * </p>
 * 
 */
public class ErrorResponse {

    /**
     * The encapsulated error details.
     */
    private final Error error;

    /**
     * Constructs an ErrorResponse instance with the specified error details.
     *
     * @param responseType The response type indicating the nature of the error.
     * @param details      Additional details describing the error.
     * @param originalUrl  The original URL with query parameters that resulted in
     *                     the error.
     * @param apiVersion   The API version associated with the error.
     * 
     */
    public ErrorResponse(ResponseType responseType, String details, String originalUrl, String apiVersion) {
        this.error = new Error(responseType.getCode(), responseType.getType(), details, originalUrl, apiVersion,
                new Date());
    }

    /**
     * Converts this ErrorResponse into its JSON representation as an ApiResponse.
     *
     * <p>
     * This method is responsible for transforming the current ErrorResponse object
     * into a format
     * suitable for inclusion in API responses. It returns an ApiResponse
     * representing the encapsulated
     * error details, complying with the expected JSON structure.
     * </p>
     *
     * @return The ApiResponse representation of this error response.
     */
    public ApiResponse toJson() {
        return this.error;
    }

    @JsonTypeName("error")
    @JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
    public static class Error implements ApiResponse {
        private final int code;
        private final String type;
        private final String details;
        private final Date timestamp;
        private final String path;
        private final String apiVersion;

        public Error(int code, String type, String details, String path, String apiVersion, Date timestamp) {
            this.code = code;
            this.type = type;
            this.details = details;
            this.apiVersion = apiVersion;
            this.timestamp = timestamp;
            this.path = path;
        }

        public int getCode() {
            return code;
        }

        public String getType() {
            return type;
        }

        public String getDetails() {
            return details;
        }

        public String getApiVersion() {
            return apiVersion;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public String getPath() {
            return path;
        }
    }
}