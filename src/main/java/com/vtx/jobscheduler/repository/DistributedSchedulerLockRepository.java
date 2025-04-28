package com.vtx.jobscheduler.repository;

import com.vtx.jobscheduler.entity.DistributedSchedulerLockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistributedSchedulerLockRepository extends JpaRepository<DistributedSchedulerLockEntity, Long> {

    @Query(value = "SELECT * FROM distributed_scheduler_lock l WHERE l.lock_key = :lockKey FOR UPDATE", nativeQuery = true)
    Optional<DistributedSchedulerLockEntity> findLockByKeyForUpdate(@Param("lockKey") String lockKey);

    DistributedSchedulerLockEntity findByLockKey(String lockKey);
}
