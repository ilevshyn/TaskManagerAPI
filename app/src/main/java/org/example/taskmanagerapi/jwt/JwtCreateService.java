package org.example.taskmanagerapi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.example.taskmanagerapi.model.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class JwtCreateService {

    private final String jwtSecret;

    public String getJwtSecret() {
        return jwtSecret;
    }

    public Optional<String> issueToken(AppUser appUser) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(getJwtSecret());
            return Optional.ofNullable(JWT.create()
                    .withSubject(String.valueOf(appUser.getId()))
                    .withIssuer("demo")
                    .withIssuedAt(Date.from(Instant.now()))
                    .withExpiresAt(Date.from(Instant.now().plusSeconds(28800)))
                    .withClaim("scp", List.of(appUser.getRole().toString()))
                    .sign(algorithm));
        } catch (JWTCreationException exception) {
            return Optional.empty();
        }
    }

    public JwtCreateService(@Value("${jwt.secret-key}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }
}
