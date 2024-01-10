package com.kood.homework.translationapi.model;

import java.util.Date;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Represents a success response in the API.
 * <p>
 * This class is designed to encapsulate details of a successful API response,
 * including
 * success code, type, details, timestamp, path, API version, and optional data.
 * It is
 * intended for serialization to JSON and implements the {@link ApiResponse}
 * interface.
 * </p>
 * 
 * <p>
 * <strong>Author:</strong> JesusKris
 * </p>
 */
public class SuccessResponse {

    /**
     * The encapsulated success response details.
     */
    private final Body body;

    /**
     * Constructs a SuccessResponse instance with the specified success details.
     *
     * @param httpStatus   The httpStatus indicating the nature of the success.
     * @param details      Additional details describing the success.
     * @param originalUrl  The original URL that resulted in the success.
     * @param apiVersion   The API version associated with the success.
     */
    public SuccessResponse(HttpStatus httpStatus, String details, String originalUrl, String apiVersion) {
        this.body = new Body(httpStatus.value(), httpStatus.getReasonPhrase(), details, originalUrl, apiVersion);
    }

    /**
     * Converts this SuccessResponse into its JSON representation as an ApiResponse.
     *
     * <p>
     * This method is responsible for transforming the current SuccessResponse
     * object into a format
     * suitable for inclusion in API responses. It returns an ApiResponse
     * representing the encapsulated
     * success details, complying with the expected JSON structure.
     * </p>
     *
     * @return The ApiResponse representation of this success response.
     */
    public ApiResponse toJson() {
        return this.body;
    }

    public void addData(String key, Object data) {
        this.body.addData(key, data);
    }

    @JsonTypeName("body")
    @JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
    public static class Body implements ApiResponse {
        private final int code;
        private final String type;
        private final String details;
        private final Date timestamp;
        private final String path;
        private final String apiVersion;
        private HashMap<String, Object> data = new HashMap<>();

        public Body(int code, String type, String details, String path, String apiVersion) {
            this.code = code;
            this.type = type;
            this.details = details;
            this.apiVersion = apiVersion;
            this.timestamp = new Date();
            this.path = path;
        }

        public void addData(String key, Object data) {
            this.data.put(key, data);
        }

        public HashMap<String, Object> getData() {
            return this.data;
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