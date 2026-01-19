package com.project.validator;

import com.project.exception.RolesRequiredException;
import com.project.model.RoleEntity;
import com.project.repository.IRoleRepository;
import com.project.service.interfaces.IRoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ResolverRoles {

    private final IRoleRepository roleRepository;

    public ResolverRoles(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<RoleEntity> resolve(Set<Long> roleIds){
        if (roleIds == null)
            throw new RolesRequiredException("User must have at least one assigned role");

        Set<RoleEntity> roleEntities =  roleIds.stream()
                .map(this.roleRepository::findById) // Transforma ID -> Optional<RoleEntity>
                .flatMap(Optional::stream)     // Filtra los vacíos y extrae el valor
                .collect(Collectors.toSet());  // Agrupa todo en un Set
        if(roleEntities.isEmpty())
            throw new RolesRequiredException("None of the provided role IDs are valid");
        return roleEntities;
    }
}
