package com.vtx.jobscheduler.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vtx.jobscheduler.enums.RoleEnum;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailsDto {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private RoleEnum role;
}
