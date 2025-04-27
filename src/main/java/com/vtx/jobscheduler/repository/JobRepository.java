package com.vtx.jobscheduler.repository;

import com.vtx.jobscheduler.entity.JobEntity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import com.vtx.jobscheduler.enums.JobStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {
    @NonNull
    Page<JobEntity> findAll(@NonNull Pageable pageable);

    Optional<JobEntity> findByName(String name);

    Optional<JobEntity> findById(Long id);

    @Query(value = "SELECT * FROM job j WHERE j.status IN (:statuses) AND j.next_run_at <= :currentTime",
            nativeQuery = true)
    List<JobEntity> findJobsToProcess(@Param("statuses") List<String> statuses,
                                      @Param("currentTime") ZonedDateTime currentTime);

    List<JobEntity> findAllByStatusIn(List<JobStatusEnum> statuses, Sort sort);

}
