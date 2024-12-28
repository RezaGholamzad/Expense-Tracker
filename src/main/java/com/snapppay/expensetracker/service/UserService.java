package com.snapppay.expensetracker.service;

import com.snapppay.expensetracker.entity.User;
import com.snapppay.expensetracker.exception.UserNotFoundException;
import com.snapppay.expensetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public final User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return user.get();
        }

        log.info("User with username {} not found", username);
        throw new UserNotFoundException("User not found");
    }

}
