package com.project.mapper;

import com.project.dto.PermissionCreationDTO;
import com.project.dto.PermissionDTO;
import com.project.model.PermissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

    PermissionEntity PermissionCreationDTOToPermissionEntity(PermissionCreationDTO permissionCreationDTO);
    PermissionDTO PermissionEntityToPermissionDTO(PermissionEntity permissionEntity);
}
