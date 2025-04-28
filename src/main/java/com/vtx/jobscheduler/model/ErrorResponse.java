package com.vtx.jobscheduler.model;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private ZonedDateTime dateTime;
    private String error;
    private String message;
    private int status;
}
