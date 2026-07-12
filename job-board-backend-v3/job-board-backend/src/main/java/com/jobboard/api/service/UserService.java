package com.jobboard.api.service;

import com.jobboard.api.dto.user.RegisterRequest;
import com.jobboard.api.dto.user.UpdateUserRequest;
import com.jobboard.api.dto.user.UserResponse;
import com.jobboard.api.exception.DuplicateResourceException;
import com.jobboard.api.exception.ResourceNotFoundException;
import com.jobboard.api.model.User;
import com.jobboard.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "An account with this email already exists");
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        return UserResponse.fromEntity(userRepository.save(user));
    }

    public UserResponse getById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No user found with id " + id));

        return UserResponse.fromEntity(user);
    }

    public UserResponse update(Long id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No user found with id " + id));

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());

        return UserResponse.fromEntity(userRepository.save(user));
    }
}