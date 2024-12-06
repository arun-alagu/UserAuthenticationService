package com.example.userauthenticationservice.services;

import com.example.userauthenticationservice.Repository.TokenRepository;
import com.example.userauthenticationservice.Repository.UserRepository;
import com.example.userauthenticationservice.exceptions.IncorrectEmailPswdException;
import com.example.userauthenticationservice.exceptions.InvalidTokenException;
import com.example.userauthenticationservice.exceptions.UserNotFoundException;
import com.example.userauthenticationservice.models.Role;
import com.example.userauthenticationservice.models.State;
import com.example.userauthenticationservice.models.Token;
import com.example.userauthenticationservice.models.User;
import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;



@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    public UserService(
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder,
            TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    public User register(String name, String email, Long phone, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setHashedPassword(passwordEncoder.encode(password));
        user.setRoles(new HashSet<>());
        return userRepository.save(user);
    }

    public Token login(String email, String password) {
        User loginUser = Optional.of(userRepository.findByEmail(email))
                .orElseThrow(()-> new UserNotFoundException("User with "+email+" not found!!\n" +
                        "Please register as new user to login"));

        if(!passwordEncoder.matches(password, loginUser.getHashedPassword()))
                throw new IncorrectEmailPswdException("Incorrect email or password");

        return getToken(loginUser);
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

    public User validateToken(String token) {
        Token optionalToken = tokenRepository.getTokenByValue(token);

        if(optionalToken == null)
            throw new InvalidTokenException("Token not valid");

        if(optionalToken.getExpiresIn() <= System.currentTimeMillis()) {
            optionalToken.setState(State.INACTIVE);
            throw new InvalidTokenException("Token is expired");
        }

        return optionalToken.getUser();
    }


}
