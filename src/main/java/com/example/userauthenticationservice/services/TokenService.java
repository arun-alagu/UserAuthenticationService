package com.example.userauthenticationservice.services;

import com.example.userauthenticationservice.Repository.TokenRepository;
import com.example.userauthenticationservice.models.State;
import com.example.userauthenticationservice.models.Token;
import com.example.userauthenticationservice.models.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token getToken(User user) {
        Optional<Token> token = Optional.ofNullable(
                tokenRepository.getTokenByUser_IdAndState(
                        user.getId(),
                        State.ACTIVE));

        if(token.isPresent()){
            if(token.get().getExpiresIn() > System.currentTimeMillis())
                return token.get();
            else
                token.get().setState(State.INACTIVE);
        }

        Token newToken = new Token();
        newToken.setUser(user);
        newToken.setValue(RandomStringUtils.secure().nextAlphanumeric(10));
        newToken.setExpiresIn(System.currentTimeMillis() + 604800000);
        return tokenRepository.save(newToken);
    }
}
