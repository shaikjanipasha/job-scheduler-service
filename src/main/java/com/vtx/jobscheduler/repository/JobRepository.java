package com.vtx.jobscheduler.repository;

import com.vtx.jobscheduler.entity.JobEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;


@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {
    @NonNull
    Page<JobEntity> findAll(@NonNull Pageable pageable);

    Optional<JobEntity> findByName(String name);

    Optional<JobEntity> findById(Long id);
}
