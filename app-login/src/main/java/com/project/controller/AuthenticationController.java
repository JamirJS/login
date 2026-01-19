package com.project.controller;

import com.project.controller.interfaces.IAuthenticationController;
import com.project.dto.auth.AuthLoginRequestDTO;
import com.project.dto.auth.AuthResponseDTO;
import com.project.service.implementations.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController implements IAuthenticationController {

    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthLoginRequestDTO authLoginRequestDTO){
        return new ResponseEntity(this.userDetailsService.loginUser(authLoginRequestDTO), HttpStatus.OK);
    }
}
