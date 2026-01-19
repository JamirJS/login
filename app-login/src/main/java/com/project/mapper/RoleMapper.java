package com.project.mapper;

import com.project.dto.RoleCreationDTO;
import com.project.dto.RoleDTO;
import com.project.model.PermissionEntity;
import com.project.model.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO RoleEntityToRoleDTO(RoleEntity roleEntity);

    @Mapping(source = "permissionIds", target = "permissionList")
    RoleEntity RoleCreationDTOToRoleEntity(RoleCreationDTO roleCreationDTO);

    // Esto permite que la lista NO llegue vacía al service
    default PermissionEntity mapIdToEntity(Long id) {
        if (id == null) return null;
        PermissionEntity permission = new PermissionEntity();
        permission.setId(id);
        return permission;
    }
}
