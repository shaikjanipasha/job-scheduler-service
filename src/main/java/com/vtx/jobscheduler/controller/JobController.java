package com.vtx.jobscheduler.controller;

import static com.vtx.jobscheduler.constants.Constants.BEARER_AUTH;
import static com.vtx.jobscheduler.routes.Routes.*;

import com.vtx.jobscheduler.model.JobPatchRequestContract;
import com.vtx.jobscheduler.model.JobRequestContract;
import com.vtx.jobscheduler.model.JobResponseContract;
import com.vtx.jobscheduler.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(API_V1_JOBS)
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @Operation(summary = "Create a new job", security = @SecurityRequirement(name = BEARER_AUTH),
            description = "Create a new job by providing job details")
    @PostMapping(JOBS_CREATE)
    public ResponseEntity<JobResponseContract> createJob(@Valid @RequestBody JobRequestContract jobRequestContract) {
        JobResponseContract jobResponseContract = jobService.createJob(jobRequestContract);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobResponseContract);
    }

    @Operation(summary = "Get job by name", security = @SecurityRequirement(name = BEARER_AUTH),
            description = "Get job details by job name.")
    @GetMapping(JOBS_BY_JOB_NAME)
    public ResponseEntity<JobResponseContract> getJobByJobName(@PathVariable String jobName) {
        JobResponseContract jobResponseContract = jobService.getJobByName(jobName);
        return ResponseEntity.ok(jobResponseContract);
    }

    @Operation(summary = "Get job by Id", security = @SecurityRequirement(name = BEARER_AUTH),
            description = "Fetch job details by jobId")
    @GetMapping(JOBS_BY_ID)
    public ResponseEntity<JobResponseContract> getJobById(@PathVariable Long jobId) {
        JobResponseContract jobResponseContract = jobService.getJobById(jobId);
        return ResponseEntity.ok(jobResponseContract);
    }

    @Operation(summary = "Patch job by Id", security = @SecurityRequirement(name = BEARER_AUTH),
            description = "Patch the job by jobId update configuration")
    @PatchMapping(JOBS_BY_ID)
    public ResponseEntity<JobResponseContract> patchJob(
            @PathVariable Long jobId,
            @Valid @RequestBody JobPatchRequestContract patchRequest) {

        JobResponseContract updatedJob = jobService.patchJob(jobId, patchRequest);
        return ResponseEntity.ok(updatedJob);
    }

    @Operation(summary = "Get all jobs with pagination", security = @SecurityRequirement(name = BEARER_AUTH),
            description = "Fetch all jobs with pagination and sorting options.")
    @GetMapping
    public ResponseEntity<Page<JobResponseContract>> getAllJobs(
            @ParameterObject Pageable pageable) {
        Page<JobResponseContract> jobs = jobService.getAllJobs(pageable);
        return ResponseEntity.ok(jobs);
    }

}
