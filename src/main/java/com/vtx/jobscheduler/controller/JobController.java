package com.vtx.jobscheduler.controller;

import com.vtx.jobscheduler.model.JobRequestContract;
import com.vtx.jobscheduler.model.JobResponseContract;
import com.vtx.jobscheduler.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @Operation(summary = "Create a new job", description = "Create a new job by providing job details")
    @PostMapping
    public ResponseEntity<JobResponseContract> createJob(@Valid @RequestBody JobRequestContract jobRequestContract) {
        JobResponseContract jobResponseContract = jobService.createJob(jobRequestContract);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobResponseContract);
    }

    @Operation(summary = "Get job by name", description = "Fetch job details by job name")
    @GetMapping("/byJobName/{jobName}")
    public ResponseEntity<JobResponseContract> getJobByJobName(@PathVariable String jobName) {
        JobResponseContract jobResponseContract = jobService.getJobByName(jobName);
        return ResponseEntity.ok(jobResponseContract);
    }

    @Operation(summary = "Get job by name", description = "Fetch job details by jobId")
    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponseContract> getJobById(@PathVariable Long jobId) {
        JobResponseContract jobResponseContract = jobService.getJobById(jobId);
        return ResponseEntity.ok(jobResponseContract);
    }

}
