package io.focuspoints.client;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.Validate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

abstract class TokenBuilder {
    
    private String issuer;
    private String secret;

    /**
     * @param tokenCreator
     */
    TokenBuilder(String issuer, String secret) {
        Validate.notBlank(issuer, "issuer is required");
        Validate.notBlank(secret, "secret is required");
        
        this.issuer = issuer;
        this.secret = secret;
    }

    private static final String CLAIM_ACTION = "action";
    
    public final String build() {
        String action = this.getAction();
        
        JWTCreator.Builder jwtBuilder = JWT.create();
        
        this.buildInternal(jwtBuilder);
        
        try {
            return jwtBuilder.withIssuer(this.issuer)
                             .withClaim(CLAIM_ACTION, action)
                             .sign(Algorithm.HMAC512(this.secret));
        } catch (UnsupportedEncodingException e) {
            throw new JWTCreationException(e.getMessage(), e);
        }
    }
    
    protected void buildInternal(JWTCreator.Builder jwtBuilder) {
    }
    
    protected abstract String getAction();
}