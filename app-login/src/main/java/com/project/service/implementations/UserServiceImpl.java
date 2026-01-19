package com.project.service.implementations;

import com.project.exception.UserEntityNotFoundException;
import com.project.exception.UserInUseException;
import com.project.model.UserEntity;
import com.project.repository.IUserRepository;
import com.project.service.interfaces.IUserService;
import com.project.validator.ResolverRoles;
import com.project.validator.UserValidator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    private final ResolverRoles resolverRoles;

    public UserServiceImpl(IUserRepository userRepository, UserValidator userValidator, PasswordEncoder passwordEncoder, ResolverRoles resolverRoles) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
        this.resolverRoles = resolverRoles;
    }

    @Override
    public void deleteUser(Long id) {
        this.userValidator.existsUserById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new UserInUseException("Cannot delete user: it has active relations in the system.");
        }
    }

    @Override
    public UserEntity findById(Long id){
        return this.userRepository.findById(id)
                .orElseThrow(() -> new UserEntityNotFoundException("User with ID" + id + " Not Found"));
    }

    @Override
    public List<UserEntity> getUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        this.enrichRoles(user);
        this.setDefaultStatus(user);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    private void enrichRoles(UserEntity user) {
        Set<Long> rolesIds = user.getRoles().stream()
                .map(role -> role.getId())
                .collect(Collectors.toSet());
        user.setRoles(this.resolverRoles.resolve(rolesIds));
    }

    private void setDefaultStatus(UserEntity user) {
        user.setEnabled(true);
        user.setAccountNoExpired(true);
        user.setAccountNoLocked(true);
        user.setCredentialNoExpired(true);
    }
}
