package com.openclassrooms.mdd.security.service.impl;

import com.openclassrooms.mdd.entity.User;
import com.openclassrooms.mdd.repository.UserRepository;
import com.openclassrooms.mdd.security.service.CustomUserDetailsService;
import com.openclassrooms.mdd.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found")
                );

        return new UserDetailsImpl(user);
    }
}