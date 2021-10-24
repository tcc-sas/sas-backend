package com.fatec.sasbackend.model.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class DefaultResponseError {

    @JsonProperty("error")
    private final String error;

    @JsonProperty("status")
    private final Integer status;

    @JsonProperty("message")
    private final Map<String, String> messages;

    @JsonProperty("timestamp")
    private final LocalDateTime timestamp;

    @JsonProperty("path")
    private final String path;

}
