package com.vtx.jobscheduler.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vtx.jobscheduler.enums.RoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationRequestContract {

    @NotNull(value = "Username cannot be null")
    private String username;

    @NotNull(value = "First name cannot be null")
    private String firstName;

    @NotNull(value = "Last name cannot be null")
    private String lastName;

    @NotNull(value = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(value = "Password cannot be null")
    @Size(min = 5, message = "Password must be at least 6 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleEnum role = RoleEnum.USER;
}
