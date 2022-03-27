package com.sistem.blog.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JWTAuthResponseDTO {

    private String tokenAccess;
    private String tokenType = "Bearer";

    public JWTAuthResponseDTO(String tokenAccess) {
        this.tokenAccess = tokenAccess;
    }
}
