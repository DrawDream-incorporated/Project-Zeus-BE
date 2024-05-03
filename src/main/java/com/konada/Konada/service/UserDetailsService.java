package com.konada.Konada.service;

import com.konada.Konada.dto.UserDto;
import com.konada.Konada.entity.User;
import com.konada.Konada.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
