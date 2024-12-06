package com.example.userauthenticationservice.controllers;

import com.example.userauthenticationservice.dtos.*;
import com.example.userauthenticationservice.models.Token;
import com.example.userauthenticationservice.models.User;
import com.example.userauthenticationservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto){
        Token token = userService.login(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword());
        return ResponseEntity.ok(TokenDto.from(token));
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequestDto logoutRequestDto){}

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto){
        User user = userService.register(
                registerRequestDto.getName(),
                registerRequestDto.getEmail(),
                registerRequestDto.getPhone(),
                registerRequestDto.getPassword());

        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    @PostMapping("/reset/password")
    public void changePassword(@RequestBody ChangePasswordDto changePasswordDto){}

    @PostMapping("/reset/email")
    public void changeEmail(){}

    @PostMapping("/reset/username")
    public void changeUsername(){}

    @GetMapping ("/validate/{token}")
    public ResponseEntity<UserResponseDto> validateToken(@PathVariable String token){
        User user = userService.validateToken(token);
        if(user == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                UserResponseDto.from(user)
        );
    }
}
