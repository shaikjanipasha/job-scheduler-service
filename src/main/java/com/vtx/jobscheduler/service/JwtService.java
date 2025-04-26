package com.vtx.jobscheduler.service;

import com.vtx.jobscheduler.enums.RoleEnum;
import com.vtx.jobscheduler.model.UserDetailsDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(String username, RoleEnum role);
    String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);

}
