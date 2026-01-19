package com.project.service.implementations;

import com.project.enumerative.RoleEnum;
import com.project.exception.RoleEntityNotFoundException;
import com.project.exception.RoleInUseException;
import com.project.model.RoleEntity;
import com.project.model.UserEntity;
import com.project.repository.IRoleRepository;
import com.project.service.interfaces.IRoleService;
import com.project.validator.ResolverPermission;
import com.project.validator.RoleValidator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;
    private final RoleValidator roleValidator;
    private final ResolverPermission resolverPermission;

    public RoleServiceImpl(IRoleRepository roleRepository, RoleValidator roleValidator, ResolverPermission resolverPermission) {
        this.roleRepository = roleRepository;
        this.roleValidator = roleValidator;
        this.resolverPermission = resolverPermission;
    }

    @Override
    public List<RoleEntity> getRoles() {
        return this.roleRepository.findAll();
    }

    @Override
    public void deleteRole(Long id) {
        this.roleValidator.existsRoleById(id);
        try {
            this.roleRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RoleInUseException("The role cannot be deleted because there are users assigned to it");
        }
    }

    @Override
    public RoleEntity getRoleByName(RoleEnum roleEnum) {
        return this.roleValidator.validateAndGetByRoleName(roleEnum);
    }

    @Override
    public RoleEntity getRoleById(Long id) {
        return this.roleValidator.validateAndGet(id);
    }

    @Override
    public RoleEntity saveRole(RoleEntity roleEntity) {
        this.enrichPermissions(roleEntity);
        return this.roleRepository.save(roleEntity);
    }

    private void enrichPermissions(RoleEntity role) {
        Set<Long> permissionIds = role.getPermissionList().stream()
                .map(permission -> permission.getId())
                .collect(Collectors.toSet());
        role.setPermissionList(this.resolverPermission.resolvePermission(permissionIds));
    }
}
