package com.vtx.jobscheduler.service.impl;

import com.vtx.jobscheduler.entity.JobEntity;
import com.vtx.jobscheduler.model.JobRequestContract;
import com.vtx.jobscheduler.model.JobResponseContract;
import com.vtx.jobscheduler.repository.JobRepository;
import com.vtx.jobscheduler.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public JobResponseContract createJob(JobRequestContract jobRequestContract) {

        return null;
    }

}
