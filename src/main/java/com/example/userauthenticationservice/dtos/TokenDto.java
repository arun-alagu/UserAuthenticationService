package com.example.userauthenticationservice.dtos;

import com.example.userauthenticationservice.models.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {
    private String value;
    private UserResponseDto user;
    private Long expiresIn;

    public static TokenDto from (Token token){
        TokenDto tokenDto = new TokenDto();
        tokenDto.value = token.getValue();
        tokenDto.user = UserResponseDto.from(token.getUser());
        tokenDto.expiresIn = token.getExpiresIn();
        return tokenDto;
    }
}
