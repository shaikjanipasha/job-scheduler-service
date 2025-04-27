package com.vtx.jobscheduler.executor;

import com.vtx.jobscheduler.entity.JobEntity;

public interface JobExecutor {
    void execute(JobEntity jobEntity);
}
