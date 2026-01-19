package com.project.dto;

import com.project.enumerative.RoleEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record RoleCreationDTO(
        @NotNull(message = "Role name is required") RoleEnum roleName,
        @NotEmpty(message = "At least one permission ID must be provided") Set<Long> permissionIds) {
}
