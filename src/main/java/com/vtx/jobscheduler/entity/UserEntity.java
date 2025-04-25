package com.vtx.jobscheduler.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "user")
public class UserEntity extends BaseEntity<UUID> {
    @Column(unique = true, nullable = false, name="user_name")
    private String username;

    @Column(nullable = false, name="encrypted_password")
    private String encryptedPassword;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true, name="email")
    private String email;
}
