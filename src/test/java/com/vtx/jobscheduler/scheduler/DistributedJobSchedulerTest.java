package com.vtx.jobscheduler.scheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.vtx.jobscheduler.entity.JobEntity;
import com.vtx.jobscheduler.enums.JobStatusEnum;
import com.vtx.jobscheduler.enums.RetryPolicyEnum;
import com.vtx.jobscheduler.enums.ScheduleTypeEnum;
import com.vtx.jobscheduler.executor.JobExecutor;
import com.vtx.jobscheduler.factory.JobExecutorFactory;
import com.vtx.jobscheduler.model.JobDetailsPayload;
import com.vtx.jobscheduler.service.DistributedSchedulerLockService;
import com.vtx.jobscheduler.service.JobService;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DistributedJobSchedulerTest {
    @Mock
    private JobService jobService;

    @Mock
    private JobExecutorFactory jobExecutorFactory;

    @Mock
    private DistributedSchedulerLockService distributedSchedulerLockService;

    @Mock
    private JobExecutor jobExecutor;

    @InjectMocks
    private DistributedJobScheduler distributedJobScheduler;

    private JobEntity job;

    @BeforeEach
    void setup() {
        job = new JobEntity();
        job.setId(1L);
        job.setName("testJob");
        job.setStatus(JobStatusEnum.CREATED);
        job.setScheduleType(ScheduleTypeEnum.CRON);
        job.setCronExpression("0 0/5 * * * ?");
        job.setFixedRateInMilliseconds(10000L);
        job.setRetryPolicy(RetryPolicyEnum.FIXED);
        job.setPayload(new JobDetailsPayload());
        job.setRetriesAttempted(0);
        job.setMaxRetries(3);
        job.setRetryDelayInSeconds(5);
        job.setExponentialBase(2);
        job.setExponentialInitialDelayInSeconds(5);
        job.setLastRunAt(ZonedDateTime.now().minusHours(1));
        job.setNextRunAt(ZonedDateTime.now());
        job.setVersion(1);
    }

    @Test
    void shouldSkipWhenNoJobsFound() {
        when(jobService.findJobsToProcess(anyList(), any())).thenReturn(Collections.emptyList());
        distributedJobScheduler.processJobs();
        verify(jobService).findJobsToProcess(anyList(), any());
        verifyNoInteractions(distributedSchedulerLockService, jobExecutorFactory, jobExecutor);
    }

    @Test
    void shouldProcessJobSuccessfully() {
        when(jobService.findJobsToProcess(anyList(), any())).thenReturn(List.of(job));
        when(distributedSchedulerLockService.tryAcquireLock(anyString())).thenReturn(true);
        when(jobExecutorFactory.getExecutor(anyString())).thenReturn(jobExecutor);

        distributedJobScheduler.processJobs();

        verify(jobExecutor).execute(job);
        verify(jobService).updateJob(job);
        verify(distributedSchedulerLockService).releaseLock(anyString());
    }

    @Test
    void shouldSkipJobWhenLockNotAcquired() {
        when(jobService.findJobsToProcess(anyList(), any())).thenReturn(List.of(job));
        when(distributedSchedulerLockService.tryAcquireLock(anyString())).thenReturn(false);

        distributedJobScheduler.processJobs();

        verify(distributedSchedulerLockService, never()).releaseLock(anyString());
        verify(jobExecutorFactory, never()).getExecutor(anyString());
        verify(jobService, never()).updateJob(any());
    }

    @Test
    void shouldRetryJobOnFailure() {
        when(jobService.findJobsToProcess(anyList(), any())).thenReturn(List.of(job));
        when(distributedSchedulerLockService.tryAcquireLock(anyString())).thenReturn(true);
        when(jobExecutorFactory.getExecutor(anyString())).thenReturn(jobExecutor);
        doThrow(new RuntimeException("Executor failed")).when(jobExecutor).execute(any());

        distributedJobScheduler.processJobs();

        assertEquals(1, job.getRetriesAttempted());
        assertEquals(JobStatusEnum.SCHEDULED, job.getStatus());
        assertNotNull(job.getNextRunAt());

        verify(jobService).updateJob(job);
        verify(distributedSchedulerLockService).releaseLock(anyString());
    }

    @Test
    void shouldMarkJobAsFailedWhenMaxRetriesExceeded() {
        job.setRetriesAttempted(3);

        when(jobService.findJobsToProcess(anyList(), any())).thenReturn(List.of(job));
        when(distributedSchedulerLockService.tryAcquireLock(anyString())).thenReturn(true);
        when(jobExecutorFactory.getExecutor(anyString())).thenReturn(jobExecutor);
        doThrow(new RuntimeException("Executor failed")).when(jobExecutor).execute(any());

        distributedJobScheduler.processJobs();

        assertEquals(JobStatusEnum.FAILED, job.getStatus());
        verify(jobService).updateJob(job);
        verify(distributedSchedulerLockService).releaseLock(anyString());
    }

}
