package com.vtx.jobscheduler.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vtx.jobscheduler.enums.RoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.util.UUID;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "user")
public class UserEntity extends BaseEntity<UUID> {
    @Column(name="user_name")
    private String username;

    @Column(name="encrypted_password")
    private String encryptedPassword;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleEnum role;

}
