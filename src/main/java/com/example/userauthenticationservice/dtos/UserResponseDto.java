package com.example.userauthenticationservice.dtos;

import com.example.userauthenticationservice.models.Role;
import com.example.userauthenticationservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponseDto {
    private String name;
    private Long phone;
    private String email;
    private List<String> roles;

    public static UserResponseDto from(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setName(user.getName());
        userResponseDto.setPhone(user.getPhone());
        userResponseDto.setEmail(user.getEmail());
        for (Role role : user.getRoles()) {
            userResponseDto.getRoles().add(role.getValue());
        }
        return userResponseDto;
    }
}
