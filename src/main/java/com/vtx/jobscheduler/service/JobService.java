package com.vtx.jobscheduler.service;

import com.vtx.jobscheduler.entity.JobEntity;
import com.vtx.jobscheduler.enums.ScheduleTypeEnum;
import com.vtx.jobscheduler.model.JobPatchRequestContract;
import com.vtx.jobscheduler.model.JobRequestContract;
import com.vtx.jobscheduler.model.JobResponseContract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;

public interface JobService {

    JobResponseContract createJob(JobRequestContract jobRequestContract);

    JobResponseContract getJobByName(String name);

    JobResponseContract getJobById(Long id);
    List<JobEntity> findJobsToProcess(List<String> statuses, ZonedDateTime currentTime);

    void updateJob(JobEntity jobEntity);

    public ZonedDateTime calculateNextRun(ScheduleTypeEnum scheduleType, String cronExpression,
                                          Long fixedRateInMilliSeconds);
    JobResponseContract patchJob(Long jobId, JobPatchRequestContract patchRequest);
    Page<JobResponseContract> getAllJobs(Pageable pageable);
}
