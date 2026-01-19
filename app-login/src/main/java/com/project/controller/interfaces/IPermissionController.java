package com.project.controller.interfaces;

import com.project.dto.PermissionCreationDTO;
import com.project.dto.PermissionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import java.util.List;

@Tag(name = "Permissions", description = "Operaciones relacionadas con la gestion de permisos")
public interface IPermissionController {

    @Operation(
            summary = "Obtener Permisos",
            description = "Obtiene la lista completa de todos los permisos registrados"
    )
    @ApiResponse(
            responseCode = "200", description = "Lista recuperada con exito"
    )
    ResponseEntity<List<PermissionDTO>> getPermissions();

    @Operation(
            summary = "Guardar permiso",
            description = "Crea un nuevo permiso basado en un valor de Enum"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Permiso creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "409", description = "El permiso ya existe")
    })
    ResponseEntity<PermissionDTO> savePermission(PermissionCreationDTO permissionCreationDTO);

    @Operation(
            summary = "Eliminar permiso",
            description = "Elimina fisicamente un permiso mediante su ID unico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Permiso eliminado con exito"),
            @ApiResponse(responseCode = "404", description = "No se encontró el permiso con el ID proporcionado")
    })
    ResponseEntity<Void> deletePermission(Long id);


    @Operation(
            summary = "Consultar permiso",
            description = "Busca los detalles de un permiso específico por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permiso encontrado"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    ResponseEntity<PermissionDTO> getPermission(Long id);
}
