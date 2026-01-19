package com.project.controller.interfaces;

import com.project.dto.ErrorResponse;
import com.project.dto.UserCreationDTO;
import com.project.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Userios", description = "Operaciones relacionadas con la gestion de usuarios")
public interface IUserController {

    @Operation(
            summary = "Guardar Usuario",
            description = "Crea un nuevo usuario dado un UserCreationDTO"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuario creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada invalidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El username ya existe",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<UserDTO> saveUser(@RequestBody UserCreationDTO userCreationDTO);

    @Operation(
            summary = "Obtener Usuarios",
            description = "Obtiene la lista completa de todos los usuarios registrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista recuperada con exito",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
    )
    ResponseEntity<List<UserDTO>> getAll();

    @Operation(
            summary = "Consultar usuario",
            description = "Busca la informacion de un usuario específico por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id);

    @Operation(
            summary = "Eliminar Usuario",
            description = "Elimina un usuario mediante su ID unico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado con exito"),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro el Usuario",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<Void> deleteUser(@PathVariable("id") Long id);

}
