package com.project.controller;

import com.project.controller.interfaces.IRoleController;
import com.project.dto.RoleCreationDTO;
import com.project.dto.RoleDTO;
import com.project.enumerative.RoleEnum;
import com.project.mapper.RoleMapper;
import com.project.model.RoleEntity;
import com.project.service.interfaces.IRoleService;
import jakarta.annotation.security.DenyAll;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/role")
@PreAuthorize("denyAll()")
public class RoleController implements IRoleController {

    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<List<RoleDTO>> getAll(){
        List<RoleDTO> roleDTOS = this.roleService.getRoles().stream()
                .map(RoleMapper.INSTANCE::RoleEntityToRoleDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roleDTOS);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<RoleDTO> getUser(@PathVariable("id") Long id){
        RoleEntity roleById = this.roleService.getRoleById(id);
        return ResponseEntity.ok(RoleMapper.INSTANCE.RoleEntityToRoleDTO(roleById));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<RoleDTO> saveRole(@Valid @RequestBody RoleCreationDTO roleCreationDTO){
        RoleEntity roleEntity = RoleMapper.INSTANCE.RoleCreationDTOToRoleEntity(roleCreationDTO);
        RoleEntity savedRole = this.roleService.saveRole(roleEntity);
        return new ResponseEntity<>(RoleMapper.INSTANCE.RoleEntityToRoleDTO(savedRole), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE')")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") Long id){
        this.roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{roleName}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<RoleDTO> getUser(@PathVariable("roleName") RoleEnum roleName){
        RoleEntity roleByEnum = this.roleService.getRoleByName(roleName);
        return ResponseEntity.ok(RoleMapper.INSTANCE.RoleEntityToRoleDTO(roleByEnum));
    }

}
