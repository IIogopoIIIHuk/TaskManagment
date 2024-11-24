package com.IIogopoIIIHuk.TaskManagment_demo.controller;

import com.IIogopoIIIHuk.TaskManagment_demo.DTO.JwtRequest;
import com.IIogopoIIIHuk.TaskManagment_demo.DTO.RegistrationUserDTO;
import com.IIogopoIIIHuk.TaskManagment_demo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "methods authentication and registration of users")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @Operation(
            summary = "authentication method",
            description = "This method authenticates a user by validating their credentials" +
                          "and returns a JWT token if the authentication is successful. " +
                          "The token can be used for accessing secured endpoints in the application."
    )
    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest){
        return authService.createAuthToken(authRequest);
    }

    @Operation(
            summary = "User registration method",
            description = "This method registers a new user by accepting their registration details" +
                          "and saving the user information in the system. " +
                          "After successful registration, the user can authenticate and access secured endpoints."
    )
    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDTO registrationUserDTO){
        return authService.createNewUser(registrationUserDTO);
    }
}
