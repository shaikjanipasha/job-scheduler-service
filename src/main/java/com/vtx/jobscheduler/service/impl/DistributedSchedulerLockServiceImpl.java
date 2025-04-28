package com.vtx.jobscheduler.service.impl;

import com.vtx.jobscheduler.entity.DistributedSchedulerLockEntity;
import com.vtx.jobscheduler.repository.DistributedSchedulerLockRepository;
import com.vtx.jobscheduler.service.DistributedSchedulerLockService;
import jakarta.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DistributedSchedulerLockServiceImpl implements DistributedSchedulerLockService {

    @Value("${scheduler.lock.expiryTimeInMinutes:30}")
    private Long lockExpiryTime;

    private String LOCK_KEY_PREFIX = "distributed_scheduler_lock::";

    private final DistributedSchedulerLockRepository lockRepository;

    @Override
    @Transactional
    public boolean tryAcquireLock(String lockName) {
        String lockKey = LOCK_KEY_PREFIX + lockName;
        Optional<DistributedSchedulerLockEntity> optExistingLockEntity = lockRepository.findLockByKeyForUpdate(lockKey);

        if (optExistingLockEntity.isEmpty()) {
            lockRepository.save(buildAndGetDistributedSchedulerLockEntity(lockKey));
            return true;
        }

        ZonedDateTime now = ZonedDateTime.now();
        DistributedSchedulerLockEntity existingLockEntity = optExistingLockEntity.get();

        if (!existingLockEntity.isLocked()) {
            // Lock is free
            existingLockEntity.setLocked(true);
            existingLockEntity.setLockTime(now);
            existingLockEntity.setExpiryTime(now.plusMinutes(lockExpiryTime));
            existingLockEntity.setLastHeartbeatTime(now);
            lockRepository.save(existingLockEntity);
            return true;
        }

        if (existingLockEntity.getExpiryTime() != null && existingLockEntity.getExpiryTime().isBefore(now)) {
            // Lock is expired, reacquire it
            existingLockEntity.setLocked(true);
            existingLockEntity.setLockTime(now);
            existingLockEntity.setExpiryTime(now.plusMinutes(lockExpiryTime));
            existingLockEntity.setLastHeartbeatTime(now);
            lockRepository.save(existingLockEntity);
            return true;
        }
        // Lock is currently held and not expired
        log.info("Lock is already acquired by another process: {}", lockKey);
        return false;
    }

    @Override
    @Transactional
    public void releaseLock(String lockName) {
        String lockKey = LOCK_KEY_PREFIX + lockName;
        Optional<DistributedSchedulerLockEntity> optLockEntity = lockRepository.findLockByKeyForUpdate(lockKey);

        if (optLockEntity.isPresent()) {
            DistributedSchedulerLockEntity lockEntity = optLockEntity.get();
            if (lockEntity.isLocked()) {
                lockEntity.setLocked(false);
                lockEntity.setLockTime(null);
                lockEntity.setExpiryTime(null);
                lockEntity.setLastHeartbeatTime(null);
                lockRepository.save(lockEntity);
                log.info("Lock released successfully: {}", lockName);
            } else {
                log.warn("Attempted to release a lock that is already unlocked: {}", lockName);
            }
        } else {
            log.warn("No lock found for key: {}", lockKey);
        }
    }

    @NotNull
    private DistributedSchedulerLockEntity buildAndGetDistributedSchedulerLockEntity(String lockKey) {
        DistributedSchedulerLockEntity lockEntity = new DistributedSchedulerLockEntity();
        lockEntity.setLockKey(lockKey);
        lockEntity.setLocked(true);
        ZonedDateTime now = ZonedDateTime.now();
        lockEntity.setLockTime(now);
        lockEntity.setExpiryTime(now.plusMinutes(lockExpiryTime));
        lockEntity.setLastHeartbeatTime(now);
        return lockEntity;
    }

}
