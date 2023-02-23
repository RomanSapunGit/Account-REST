package com.myproject.accountrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.http.HttpStatusCode;

import java.sql.Timestamp;

@Builder
public class ResponseExceptionDTO {
    @JsonProperty("exception")
    private String exception;
    @JsonProperty("timestamp")
    private Timestamp timestamp;
    @JsonProperty("status")
    private HttpStatusCode statusCode;
    @JsonProperty("message")
    private String message;
}
