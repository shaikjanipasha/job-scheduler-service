package com.vtx.jobscheduler.factory;

import com.vtx.jobscheduler.executor.DefaultJobExecutor;
import com.vtx.jobscheduler.executor.JobExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobExecutorFactory {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DefaultJobExecutor defaultJobExecutor;

    public JobExecutor getExecutor(String jobName) {
        log.debug("Loading JobExecutor for job: {}", jobName);
        String normalizedJobName = jobName.toLowerCase();
        if (applicationContext.containsBean(normalizedJobName)) {
            return (JobExecutor) applicationContext.getBean(normalizedJobName);
        } else {
            return defaultJobExecutor;
        }
    }

}
