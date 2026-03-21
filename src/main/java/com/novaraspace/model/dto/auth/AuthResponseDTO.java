package com.novaraspace.model.dto.auth;

import org.springframework.http.ResponseCookie;

public class AuthResponseDTO {
    private String jwt;
    private ResponseCookie cookie;


    public String getJwt() {
        return jwt;
    }

    public AuthResponseDTO setJwt(String jwt) {
        this.jwt = jwt;
        return this;
    }

    public ResponseCookie getCookie() {
        return cookie;
    }

    public AuthResponseDTO setCookie(ResponseCookie cookie) {
        this.cookie = cookie;
        return this;
    }
}
