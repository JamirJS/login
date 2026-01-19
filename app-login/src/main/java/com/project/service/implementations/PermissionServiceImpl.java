package com.project.service.implementations;

import com.project.exception.PermissionInUseException;
import com.project.model.PermissionEntity;
import com.project.repository.IPermissionRepository;
import com.project.service.interfaces.IPermissionService;
import com.project.validator.PermissionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private IPermissionRepository permissionRepository;

    @Autowired
    private PermissionValidator permissionValidator;

    @Override
    public PermissionEntity savePermission(PermissionEntity permissionEntity) {
        return this.permissionRepository.save(permissionEntity);
    }

    @Override
    public List<PermissionEntity> getPermissions() {
        return this.permissionRepository.findAll();
    }

    @Override
    public void deletePermission(Long id) {
        this.permissionValidator.existsPermissionById(id);
        try {
            this.permissionRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new PermissionInUseException("The permission cannot be deleted because there are roles assigned to it");
        }

    }

    @Override
    public PermissionEntity findById(Long id) {

        return this.permissionValidator.validateAndGet(id);
    }
}
