package com.example.userauthenticationservice.controllers;

import com.example.userauthenticationservice.dtos.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/login")
    public UserDto login(@RequestBody LoginRequestDto loginRequestDto){
        return null;
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequestDto logoutRequestDto){}

    @PostMapping("/register")
    public UserDto register(@RequestBody RegisterRequestDto registerRequestDto){
        return null;
    }

    @PostMapping("/reset/password")
    public void changePassword(@RequestBody ChangePasswordDto changePasswordDto){}

    @PostMapping("/reset/email")
    public void changeEmail(){}

    @PostMapping("/reset/username")
    public void changeUsername(){}

    @PostMapping("/token")
    public void validateToken(@RequestBody ValidateTokenRequestDto validateTokenRequestDto){}
}
