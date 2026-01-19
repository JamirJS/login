package com.project.validator;

import com.project.model.PermissionEntity;
import com.project.repository.IPermissionRepository;
import com.project.service.interfaces.IPermissionService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ResolverPermission {

    private final IPermissionRepository permissionRepository;

    public ResolverPermission(IPermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Set<PermissionEntity> resolvePermission(Set<Long> permissions){
        Set<PermissionEntity> permissionList = new HashSet<>();

        permissions.forEach(permissionID -> {
            PermissionEntity auxPermission = this.permissionRepository.findById(permissionID).orElse(null);
            if(auxPermission != null)
                permissionList.add(auxPermission);
        });
        return permissionList;
    }
}
