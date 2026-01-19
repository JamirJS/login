package com.project.validator;

import com.project.exception.PermissionEntityNotFoundException;
import com.project.model.PermissionEntity;
import com.project.repository.IPermissionRepository;
import org.springframework.stereotype.Service;

@Service
public class PermissionValidator {

    private final IPermissionRepository permissionRepository;

    public PermissionValidator(IPermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public void existsPermissionById(Long id){
        if(!this.permissionRepository.existsById(id))
            throw new PermissionEntityNotFoundException("Permission with ID " + id + " Not Found");
    }

    public PermissionEntity validateAndGet(Long id){
        return this.permissionRepository.findById(id)
                .orElseThrow(() -> new PermissionEntityNotFoundException("Permission withc ID " + id + " Not Found"));
    }
}
