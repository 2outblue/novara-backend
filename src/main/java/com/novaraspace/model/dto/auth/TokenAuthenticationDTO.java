package com.novaraspace.model.dto.auth;

public class TokenAuthenticationDTO {
    private String refreshToken;
    private String jwt;

    public TokenAuthenticationDTO() {
    }

    public TokenAuthenticationDTO(String refreshToken, String jwt) {
        this.refreshToken = refreshToken;
        this.jwt = jwt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public TokenAuthenticationDTO setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public String getJwt() {
        return jwt;
    }

    public TokenAuthenticationDTO setJwt(String jwt) {
        this.jwt = jwt;
        return this;
    }
}
