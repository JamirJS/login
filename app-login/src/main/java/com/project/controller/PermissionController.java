package com.project.controller;

import com.project.controller.interfaces.IPermissionController;
import com.project.dto.PermissionCreationDTO;
import com.project.dto.PermissionDTO;
import com.project.mapper.PermissionMapper;
import com.project.model.PermissionEntity;
import com.project.service.interfaces.IPermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/permission")
@RequiredArgsConstructor
@PreAuthorize("denyAll()")
public class PermissionController implements IPermissionController {

    private final IPermissionService permissionService;

    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<List<PermissionDTO>> getPermissions(){
        List<PermissionDTO> permissionDTOS = this.permissionService.getPermissions().stream()
                .map(PermissionMapper.INSTANCE::PermissionEntityToPermissionDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(permissionDTOS);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<PermissionDTO> savePermission(@Valid @RequestBody PermissionCreationDTO permissionCreationDTO){
        PermissionEntity permissionEntity = PermissionMapper.INSTANCE.PermissionCreationDTOToPermissionEntity(permissionCreationDTO);
        System.out.println("es " + permissionEntity.getPermissionName());
        PermissionEntity savedPermission = this.permissionService.savePermission(permissionEntity);
        return new ResponseEntity<>(PermissionMapper.INSTANCE.PermissionEntityToPermissionDTO(savedPermission), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE')")
    public ResponseEntity<Void> deletePermission(@PathVariable("id") Long id){
        this.permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<PermissionDTO> getPermission(@PathVariable("id") Long id){
        PermissionEntity permission = this.permissionService.findById(id);
        return ResponseEntity.ok(PermissionMapper.INSTANCE.PermissionEntityToPermissionDTO(permission));
    }

}
