package com.project.controller.interfaces;

import com.project.dto.auth.AuthLoginRequestDTO;
import com.project.dto.auth.AuthResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication", description = "Login")
public interface IAuthenticationController {

    @Operation(
            summary = "Login",
            description = "Inicio de sesion dado un username y password"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitosamente"),
            @ApiResponse(responseCode = "401", description = "Invalid username or passsword")
    })
    ResponseEntity<AuthResponseDTO> login(AuthLoginRequestDTO authLoginRequestDTO);
}
