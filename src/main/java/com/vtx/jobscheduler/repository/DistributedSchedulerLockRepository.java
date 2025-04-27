package com.vtx.jobscheduler.repository;

import com.vtx.jobscheduler.entity.DistributedSchedulerLockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributedSchedulerLockRepository extends JpaRepository<DistributedSchedulerLockEntity, Long> {

    DistributedSchedulerLockEntity findByLockKey(String lockKey);
}
