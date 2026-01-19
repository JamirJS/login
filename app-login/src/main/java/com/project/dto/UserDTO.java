package com.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private Long id;

    private String username;


    private boolean isEnabled;

    private Set<RoleDTO> roles = new HashSet<>();
}
