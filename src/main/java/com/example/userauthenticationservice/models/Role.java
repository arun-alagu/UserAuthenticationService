package com.example.userauthenticationservice.models;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role extends BaseModel {
    private String value;
}
