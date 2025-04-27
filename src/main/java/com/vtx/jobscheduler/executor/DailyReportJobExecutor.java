package com.vtx.jobscheduler.executor;

import com.vtx.jobscheduler.entity.JobEntity;
import com.vtx.jobscheduler.enums.JobStatusEnum;
import com.vtx.jobscheduler.model.JobDetailsPayload;
import com.vtx.jobscheduler.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("dailyreportjob")
@RequiredArgsConstructor
@Slf4j
public class DailyReportJobExecutor implements JobExecutor {

    private final JobService jobService;

    @Override
    public void execute(JobEntity jobEntity) {
        log.info("####################################################");
        log.info("Start - DailyReportJobExecutor executeMethod()");
        log.info("Executing Daily Report Job with ID: {} and Name: {}", jobEntity.getId(), jobEntity.getName());
        log.info("This is a placeholder for the actual job logic. ");
        log.info("End - DailyReportJobExecutor executeMethod()");
        log.info("####################################################");
    }
}
