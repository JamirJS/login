package com.project.controller.interfaces;

import com.project.dto.RoleCreationDTO;
import com.project.dto.RoleDTO;
import com.project.enumerative.RoleEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Roles", description = "Operaciones relacionadas con la gestion de roles")
public interface IRoleController {

    @Operation(
            summary = "Obtener Roles",
            description = "Obtiene la lista completa de todos los roles registrados"
    )
    @ApiResponse(
            responseCode = "200", description = "Lista recuperada con exito"
    )
    ResponseEntity<List<RoleDTO>> getAll();

    @Operation(
            summary = "Consultar rol",
            description = "Busca los detalles de un rol específico por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    ResponseEntity<RoleDTO> getUser(@PathVariable("id") Long id);

    @Operation(
            summary = "Guardar Rol",
            description = "Crea un nuevo rol basado en un valor de Enum"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rol creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
            @ApiResponse(responseCode = "409", description = "El rol ya existe")
    })
    ResponseEntity<RoleDTO> saveRole(@Valid @RequestBody RoleCreationDTO roleCreationDTO);

    @Operation(
            summary = "Eliminar rol",
            description = "Elimina un permiso mediante su ID unico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rol eliminado con exito"),
            @ApiResponse(responseCode = "404", description = "No se encontro el rol con el ID proporcionado")
    })
    ResponseEntity<Void> deleteRole(@PathVariable("id") Long id);

    @Operation(
            summary = "Consultar rol mediante un Enum",
            description = "Busca los detalles de un rol específico dado un Enum"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    ResponseEntity<RoleDTO> getUser(@PathVariable("roleName") RoleEnum roleName);
}
