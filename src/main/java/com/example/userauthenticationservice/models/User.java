package com.example.userauthenticationservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class User extends BaseModel{
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, length = 15)
    private Long phone;
    private String hashedPassword;
    private String isEmailVerified = "false";
    @ManyToMany
    private Set<Role> roles = new HashSet<>();
}
