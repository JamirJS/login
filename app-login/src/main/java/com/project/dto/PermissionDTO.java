package com.project.dto;

import com.project.enumerative.PermissionEnum;


public record PermissionDTO(
        Long id,
        PermissionEnum permissionName){
}
