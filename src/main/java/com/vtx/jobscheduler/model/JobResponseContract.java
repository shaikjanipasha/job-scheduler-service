package com.vtx.jobscheduler.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobResponseContract extends Contract {
    private ZonedDateTime lastRunAt;
    private ZonedDateTime nextRunAt;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
