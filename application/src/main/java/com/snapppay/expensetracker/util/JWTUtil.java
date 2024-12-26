package com.snapppay.expensetracker.util;

import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

import java.nio.charset.StandardCharsets;
import java.security.Key;

public class JWTUtil {

    private static final String SECRET_KEY = "my-very-secure-and-long-secret-key-123456";
    private static final String ALGORITHM = "HS256";

    public static String decodeJwt(String jwt) {
        try {
            Key key = new HmacKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setRequireSubject() // require the 'sub' claim to be present
                    .setVerificationKey(key) // set the key for verification
                    .build();

            JwtClaims claims = jwtConsumer.processToClaims(jwt);

            return claims.getSubject();
        } catch (InvalidJwtException | MalformedClaimException e) {
            throw new RuntimeException(e);
        }

    }

    public static String createJwt(Integer id) throws JoseException {
        // Create the Claims
        JwtClaims claims = new JwtClaims();
        claims.setSubject(String.valueOf(id));
        claims.setExpirationTimeMinutesInTheFuture(10); // Token valid for 10 minutes

        // Create the JWT header
        JsonWebSignature jws = new JsonWebSignature();
        jws.setHeader("typ", "JWT");
        jws.setHeader("alg", ALGORITHM);
        jws.setPayload(claims.toJson());
        Key key = new HmacKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        jws.setKey(key);

        // Sign the JWT and return it
        return jws.getCompactSerialization();
    }

}
