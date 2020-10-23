package com.example.shortening.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
@Getter
@AllArgsConstructor
public class JwtResponse implements Serializable {

    private final String jwtToken;
}
