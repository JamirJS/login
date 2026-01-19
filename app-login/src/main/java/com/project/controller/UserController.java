package com.project.controller;

import com.project.dto.UserCreationDTO;
import com.project.dto.UserDTO;
import com.project.mapper.UserMapper;
import com.project.model.UserEntity;
import com.project.service.interfaces.IUserService;
import com.project.validator.ResolverRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("denyAll()")
public class UserController {

    private final IUserService userService;
    private final ResolverRoles resolverRoles;

    public UserController(IUserService userService, ResolverRoles resolverRoles) {
        this.userService = userService;
        this.resolverRoles = resolverRoles;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserCreationDTO userCreationDTO){
        UserEntity userEntity = UserMapper.INSTANCE.UserCreationDTOToEntity(userCreationDTO);
        userEntity.setRoles(resolverRoles.resolve(userCreationDTO.roleIds()));
        UserEntity savedUser = this.userService.saveUser(userEntity);
        return new ResponseEntity<>(UserMapper.INSTANCE.UserEntityToUserDTO(savedUser), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAll(){
        List<UserDTO> userDTOS = this.userService.getUsers().stream()
                .map(UserMapper.INSTANCE::UserEntityToUserDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOS);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id){
        UserEntity user = this.userService.findById(id);
        return ResponseEntity.ok(UserMapper.INSTANCE.UserEntityToUserDTO(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE')")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}
