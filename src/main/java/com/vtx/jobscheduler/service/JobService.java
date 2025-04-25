package com.vtx.jobscheduler.service;

import com.vtx.jobscheduler.entity.JobEntity;
import com.vtx.jobscheduler.model.JobRequestContract;
import com.vtx.jobscheduler.model.JobResponseContract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobService {

    JobResponseContract createJob(JobRequestContract jobRequestContract);

/*
    JobEntity updateJob(Long id, JobEntity jobEntity);

    void deleteJob(Long id);

    Page<JobEntity> getAllJobs(Pageable pageable);

    JobEntity getJobById(Long id);

    JobEntity getJobByName(String name); */
}
