package com.vtx.jobscheduler.service;

public interface DistributedSchedulerLockService {
    public boolean tryAcquireLock(String lockName);
    public void releaseLock(String lockName);
}
