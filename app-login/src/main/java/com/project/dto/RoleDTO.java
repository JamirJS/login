package com.project.dto;

import com.project.enumerative.RoleEnum;

import java.util.HashSet;
import java.util.Set;

public record RoleDTO(
        Long id,
        RoleEnum roleName,
        Set<PermissionDTO> permissionList){
}
