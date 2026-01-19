package com.project.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UserCreationDTO(@NotNull String username,
                              @NotNull String password,
                              @NotEmpty Set<Long> roleIds
                              ) {
}
