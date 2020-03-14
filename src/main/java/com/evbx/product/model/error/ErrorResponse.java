package com.evbx.product.model.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ErrorResponse {

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("error")
    private String error;

    @JsonProperty("errorMessages")
    private List<String> errorMessages;

    @JsonProperty("message")
    private String message;

    @JsonProperty("path")
    private String path;
}
