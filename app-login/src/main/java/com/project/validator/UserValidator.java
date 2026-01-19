package com.project.validator;

import com.project.exception.UserEntityNotFoundException;
import com.project.repository.IUserRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {

    private final IUserRepository userRepository;

    public UserValidator(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void existsUserById(Long id){
        this.userRepository.findById(id)
                .orElseThrow(() -> new UserEntityNotFoundException("User with ID "+ id + " Not Found"));
    }
}
