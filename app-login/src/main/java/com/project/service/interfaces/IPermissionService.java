package com.project.service.interfaces;

import com.project.model.PermissionEntity;

import java.util.List;
import java.util.Optional;

public interface IPermissionService {

    public PermissionEntity savePermission(PermissionEntity permissionEntity);

    public List<PermissionEntity> getPermissions();

    public void deletePermission(Long id);

    public PermissionEntity findById(Long id);
}