package com.example.foodstep.service;

import com.example.foodstep.domain.User;
import com.example.foodstep.dto.user.UserDto;
import com.example.foodstep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + ": Invalid Email"));
        return new UserDto(user);

        /*GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getAuthority().toString());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(grantedAuthority)
        );*/
    }
}
