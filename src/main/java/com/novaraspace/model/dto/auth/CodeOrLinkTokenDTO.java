package com.novaraspace.model.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CodeOrLinkTokenDTO {

    @NotBlank
    @Size(min = 6,max = 200)
    private String codeOrLinkToken;

    public String getCodeOrLinkToken() {
        return codeOrLinkToken;
    }

    public CodeOrLinkTokenDTO setCodeOrLinkToken(String codeOrLinkToken) {
        this.codeOrLinkToken = codeOrLinkToken;
        return this;
    }
}
