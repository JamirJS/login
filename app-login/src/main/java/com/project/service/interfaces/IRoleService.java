package com.project.service.interfaces;

import com.project.enumerative.RoleEnum;
import com.project.model.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    public List<RoleEntity> getRoles();

    public void deleteRole(Long id);

    public RoleEntity getRoleByName(RoleEnum roleEnum);

    public RoleEntity getRoleById(Long id);

    public RoleEntity saveRole(RoleEntity roleEntity);
}
