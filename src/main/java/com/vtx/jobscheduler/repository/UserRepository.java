package com.vtx.jobscheduler.repository;

import com.vtx.jobscheduler.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;

import com.vtx.jobscheduler.model.UserDetailsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);
}
