package com.seamhealthgroup.diagbot.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Errors {
    private final String responseCode;
    private final String responseMessage;
    private Object data;

    Errors(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }
}
