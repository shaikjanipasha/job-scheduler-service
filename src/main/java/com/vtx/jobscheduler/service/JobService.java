package com.vtx.jobscheduler.service;

import com.vtx.jobscheduler.entity.JobEntity;
import com.vtx.jobscheduler.model.JobRequestContract;
import com.vtx.jobscheduler.model.JobResponseContract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobService {

    JobResponseContract createJob(JobRequestContract jobRequestContract);

    JobResponseContract getJobByName(String name);

    JobResponseContract getJobById(Long id);

/*
    JobEntity updateJob(Long id, JobEntity jobEntity);

    void deleteJob(Long id);

    Page<JobEntity> getAllJobs(Pageable pageable);



    JobEntity getJobByName(String name); */
}
