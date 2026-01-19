package com.project.service.interfaces;

import com.project.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    public void deleteUser(Long id);

    public UserEntity findById(Long id);

    public List<UserEntity> getUsers();

    public UserEntity saveUser(UserEntity user);
}