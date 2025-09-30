package com.novaraspace.config;

import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithms;

public class JWKAlgorithmImpl implements JwsAlgorithm {
    @Override
    public String getName() {
        return JwsAlgorithms.HS256;
    }
}
