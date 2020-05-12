package se.expleostockholm.signup.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;
    public static final long EXPIRATION_TIME = 999_000_000;

    /**
     * Extract username from token.
     *
     * @param token
     * @return username
     * @throws ExpiredJwtException
     */
    public String extractUsername(String token) throws ExpiredJwtException {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract expiration date.
     *
     * @param token
     * @return
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract claim.
     *
     * @param token
     * @param claimsResolver
     * @param <T>
     * @return
     * @throws ExpiredJwtException
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws ExpiredJwtException {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims.
     *
     * @param token
     * @return
     * @throws ExpiredJwtException
     */
    private Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
    }

    /**
     * Checks if token has expired.
     *
     * @param token
     * @return
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Generate token.
     *
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Create token.
     *
     * @param claims
     * @param subject
     * @return
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
                .compact();
    }

    /**
     * Create reset password token.
     *
     * @param email
     * @return
     */
    public String createResetPasswordToken(String email) {
        final long resetPasswordExpirationTime = 1_800_000;
        return Jwts.builder().setClaims(new HashMap<>()).setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + resetPasswordExpirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
    }

    /**
     * Checks if token is valid.
     *
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

