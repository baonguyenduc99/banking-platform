// package com.banking.common.security.jwt;

// import com.banking.common.exception.ErrorCode;
// import com.banking.common.security.JwtValidationResult;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.ExpiredJwtException;
// import io.jsonwebtoken.JwtParser;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.MalformedJwtException;
// import io.jsonwebtoken.UnsupportedJwtException;
// import io.jsonwebtoken.security.Keys;
// import lombok.extern.slf4j.Slf4j;

// import javax.crypto.SecretKey;
// import java.nio.charset.StandardCharsets;
// import java.security.SignatureException;
// import java.util.Set;
// import java.util.UUID;

// @Slf4j
// public class DefaultJwtValidator implements JwtValidator {

// private final JwtParser parser;

// /**
// * Constructs a DefaultJwtValidator with the specified secret key.
// * Initializes the JWT parser with HMAC SHA signing using the provided secret,
// * and configures it to require a specific issuer for token validation.
// *
// * @param secret the secret key used for HMAC SHA signing of JWT tokens
// */
// public DefaultJwtValidator(String secret) {
// this.parser =
// Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(secret.getBytes())))
// .build();
// }

// public JwtValidationResult validateToken (String jwt){
// try{
// Claims claims = parser.parseClaimsJws(jwt).getBody();
// return new JwtValidationResult(
// true, UUID.fromString(claims.getSubject()),
// Set<String>.of(claims.get("roles", String.class).split(","),
// null)
// );
// } catch (ExpiredJwtException e){
// log.warn("JWT expired at {}", e.getClaims().getExpiration());
// return new JwtValidationResult(false, null, null,
// ErrorCode.AUTH_TOKEN_INVALID);
// } catch (UnsupportedJwtException e){
// log.warn("Unsupported JWT: {}", e.getMessage());
// return new JwtValidationResult(false, null, null,
// ErrorCode.AUTH_TOKEN_INVALID);
// } catch (MalformedJwtException e){
// log.warn("Malformed JWT: {}", e.getMessage());
// return new JwtValidationResult(false, null, null,
// ErrorCode.AUTH_TOKEN_INVALID);
// } catch (SignatureException ex) {
// log.warn("Invalid JWT signature");
// return new JwtValidationResult(false, null, null,
// ErrorCode.AUTH_TOKEN_INVALID);
// }
// }
// }
