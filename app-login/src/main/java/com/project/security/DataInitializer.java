package com.project.security;

import com.project.enumerative.PermissionEnum;
import com.project.enumerative.RoleEnum;
import com.project.exception.RoleEntityNotFoundException;
import com.project.model.PermissionEntity;
import com.project.model.RoleEntity;
import com.project.model.UserEntity;
import com.project.repository.IPermissionRepository;
import com.project.repository.IRoleRepository;
import com.project.repository.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IPermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(IUserRepository userRepository, IRoleRepository roleRepository, IPermissionRepository permissionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(this.userRepository.count() == 0){
            this.createRoleIfNotFound();
            String username = System.getenv("ADMIN_USERNAME");
            String password = System.getenv("ADMIN_PASSWORD");

            RoleEntity adminRole = this.roleRepository.findByRoleName(RoleEnum.ADMIN)
                    .orElseThrow(() -> new RoleEntityNotFoundException("Admin Role Entity Not Found"));

            UserEntity admin = UserEntity.builder()
                    .username(username)
                    .password(this.passwordEncoder.encode(password))
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(adminRole))
                    .build();
            this.userRepository.save(admin);
        }
    }

    private void createRoleIfNotFound() {
        // 1. Definir los permisos que queremos asegurar que existan
        List<PermissionEnum> requiredPermissions = List.of(
                PermissionEnum.READ,
                PermissionEnum.DELETE,
                PermissionEnum.UPDATE,
                PermissionEnum.CREATE
        );

        Set<PermissionEntity> permissionEntities = new HashSet<>();

        requiredPermissions.stream()
                .forEach(permissionEnum -> {
                    PermissionEntity permission = this.permissionRepository.findByPermissionName(permissionEnum)
                            .orElseGet(() -> this.permissionRepository.save(
                                    PermissionEntity.builder()
                                            .permissionName(permissionEnum)
                                            .build()
                            ));
                    permissionEntities.add(permission);
                });

        /*
        this.roleRepository.findByRoleName(RoleEnum.ADMIN).ifPresentOrElse(
                role -> {
                    // Si el rol existe, actualizamos sus permisos por si agregaste nuevos
                    role.setPermissionList(permissionEntities);
                    roleRepository.save(role);
                },
                () -> {
                    // Si el rol no existe, lo creamos
                    RoleEntity newRole = RoleEntity.builder()
                            .roleName(RoleEnum.ADMIN)
                            .permissionList(permissionEntities)
                            .build();
                    roleRepository.save(newRole);
                }
        );*/


        if (this.roleRepository.findByRoleName(RoleEnum.ADMIN).isEmpty()) {
            RoleEntity role = new RoleEntity();
            role.setRoleName(RoleEnum.ADMIN);
            role.setPermissionList(permissionEntities);
            this.roleRepository.save(role);
        }
    }
}
