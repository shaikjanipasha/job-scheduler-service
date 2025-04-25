package com.vtx.jobscheduler.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDetailsPayload {
    private String action;
    Map<String, Object> params;
    Map<String, Object> contexts;
}
