package com.novaraspace.model.dto.auth;

public class VerificationTokenDTO {

    private String email;
    private String code;
    private String linkToken;

    public String getEmail() {
        return email;
    }

    public VerificationTokenDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getCode() {
        return code;
    }

    public VerificationTokenDTO setCode(String code) {
        this.code = code;
        return this;
    }

    public String getLinkToken() {
        return linkToken;
    }

    public VerificationTokenDTO setLinkToken(String linkToken) {
        this.linkToken = linkToken;
        return this;
    }
}
