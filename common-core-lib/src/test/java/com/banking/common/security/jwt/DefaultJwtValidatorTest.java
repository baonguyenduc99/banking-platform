package com.banking.common.security.jwt;

import com.banking.common.exception.ErrorCode;
import com.banking.common.security.JwtValidationResult;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class DefaultJwtValidatorTest {

    private static final String SECRET = "01234567890123456789012345678901"; // 32 bytes for HS256

    private String generateJwt(UUID subject, String roles, Date expiration) {
        return Jwts.builder()
                .setSubject(subject.toString())
                .claim("roles", roles)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }

    @Test
    void testValidateToken_validToken() {
        UUID userId = UUID.randomUUID();
        String roles = "ADMIN,USER";
        Date expiration = new Date(System.currentTimeMillis() + 10000);
        String jwt = generateJwt(userId, roles, expiration);

        DefaultJwtValidator validator = new DefaultJwtValidator(SECRET);
        JwtValidationResult result = validator.validateToken(jwt);

        assertTrue(result.isValid());
        assertEquals(userId, result.getUserId());
        assertEquals(Set.of("ADMIN", "USER"), result.getRoles());
        assertNull(result.getErrorCode());
    }

    @Test
    void testValidateToken_expiredToken() {
        UUID userId = UUID.randomUUID();
        String roles = "ADMIN";
        Date expiration = new Date(System.currentTimeMillis() - 1000);
        String jwt = generateJwt(userId, roles, expiration);

        DefaultJwtValidator validator = new DefaultJwtValidator(SECRET);
        JwtValidationResult result = validator.validateToken(jwt);

        assertFalse(result.isValid());
        assertNull(result.getUserId());
        assertNull(result.getRoles());
        assertEquals(ErrorCode.AUTH_TOKEN_INVALID, result.getErrorCode());
    }

    @Test
    void testValidateToken_malformedToken() {
        DefaultJwtValidator validator = new DefaultJwtValidator(SECRET);
        String malformedJwt = "not.a.jwt.token";

        JwtValidationResult result = validator.validateToken(malformedJwt);

        assertFalse(result.isValid());
        assertNull(result.getUserId());
        assertNull(result.getRoles());
        assertEquals(ErrorCode.AUTH_TOKEN_INVALID, result.getErrorCode());
    }

    @Test
    void testValidateToken_invalidSignature() {
        UUID userId = UUID.randomUUID();
        String roles = "USER";
        Date expiration = new Date(System.currentTimeMillis() + 10000);
        String jwt = Jwts.builder()
                .setSubject(userId.toString())
                .claim("roles", roles)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, "wrongsecretwrongsecretwrongsecret12".getBytes())
                .compact();

        DefaultJwtValidator validator = new DefaultJwtValidator(SECRET);
        JwtValidationResult result = validator.validateToken(jwt);

        assertFalse(result.isValid());
        assertNull(result.getUserId());
        assertNull(result.getRoles());
        assertEquals(ErrorCode.AUTH_TOKEN_INVALID, result.getErrorCode());
    }
}