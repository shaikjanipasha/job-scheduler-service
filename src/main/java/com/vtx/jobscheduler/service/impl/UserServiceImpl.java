package com.vtx.jobscheduler.service.impl;

import com.vtx.jobscheduler.entity.UserEntity;
import com.vtx.jobscheduler.model.RegistrationRequestContract;
import com.vtx.jobscheduler.model.UserDetailsDto;
import com.vtx.jobscheduler.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsDto getUserDetailsByUserName(String username) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (optUser.isPresent()) {
            UserEntity user = optUser.get();
            return UserDetailsDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();
        }
        return null;
    }


    public void saveUser(RegistrationRequestContract registrationRequestContract) {
        UserEntity user = new UserEntity();
        user.setUsername(registrationRequestContract.getUsername());
        user.setEncryptedPassword(registrationRequestContract.getPassword());
        user.setFirstName(registrationRequestContract.getFirstName());
        user.setLastName(registrationRequestContract.getLastName());
        user.setEmail(registrationRequestContract.getEmail());
        user.setRole(registrationRequestContract.getRole());
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getEncryptedPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().getValue())));
    }
}
