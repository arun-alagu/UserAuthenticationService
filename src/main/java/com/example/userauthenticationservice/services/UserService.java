package com.example.userauthenticationservice.services;

import com.example.userauthenticationservice.Repository.UserRepository;
import com.example.userauthenticationservice.configs.PasswordEncoder;
import com.example.userauthenticationservice.exceptions.IncorrectEmailPswdException;
import com.example.userauthenticationservice.exceptions.UserNotFoundException;
import com.example.userauthenticationservice.models.Token;
import com.example.userauthenticationservice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            TokenService tokenService,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String name, String email, Long phone, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setHashedPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }

    public Token login(String email, String password) {
        User loginUser = Optional.of(userRepository.findByEmail(email))
                .orElseThrow(()-> new UserNotFoundException("User with "+email+" not found!!\n" +
                        "Please register as new user to login"));

        if(!passwordEncoder.matches(password, loginUser.getHashedPassword()))
                throw new IncorrectEmailPswdException("Incorrect email or password");

        return tokenService.getToken(loginUser);
    }


}
