package com.project.validator;

import com.project.enumerative.RoleEnum;
import com.project.exception.RoleEntityNotFoundException;
import com.project.model.RoleEntity;
import com.project.repository.IRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleValidator {

    private final IRoleRepository roleRepository;


    public RoleValidator(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleEntity validateAndGet(Long id){
        RoleEntity roleEntity = this.roleRepository.findById(id)
                .orElseThrow(() -> new RoleEntityNotFoundException("Role with ID " + id + " Not Found"));
        return roleEntity;
    }

    public RoleEntity validateAndGetByRoleName(RoleEnum roleName){
        RoleEntity roleEntity = this.roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RoleEntityNotFoundException("Role with Role Name " + roleName + " Not Found"));
        return roleEntity;
    }

    public void existsRoleById(Long id){
        this.roleRepository.findById(id)
                .orElseThrow(() -> new RoleEntityNotFoundException("Role with ID " + id + " Not Found"));

    }
}
