package com.example.shortening.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class JwtRequest implements Serializable {

    private String username;
    private String password;
}





