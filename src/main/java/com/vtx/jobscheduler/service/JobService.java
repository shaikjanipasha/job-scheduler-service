package com.vtx.jobscheduler.service;

import com.vtx.jobscheduler.entity.JobEntity;
import com.vtx.jobscheduler.enums.ScheduleTypeEnum;
import com.vtx.jobscheduler.model.JobRequestContract;
import com.vtx.jobscheduler.model.JobResponseContract;

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



/*
    JobEntity updateJob(Long id, JobEntity jobEntity);

    void deleteJob(Long id);

    Page<JobEntity> getAllJobs(Pageable pageable);



    JobEntity getJobByName(String name); */
}
