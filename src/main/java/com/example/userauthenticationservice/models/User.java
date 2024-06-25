package com.example.userauthenticationservice.models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class User extends BaseModel{
    private String username;
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();

}
