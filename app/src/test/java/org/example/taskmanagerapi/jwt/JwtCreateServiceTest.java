package org.example.taskmanagerapi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.example.taskmanagerapi.model.AppUser;
import org.example.taskmanagerapi.model.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtCreateServiceTest {

    AppUser mockAppUser = new AppUser("user1", "1234", Role.USER);

    JwtCreateService jwtCreateService = new JwtCreateService("0054576d096f4c377b62197644d3b29221b65d2674148acda2fbaa6910f17c83");

    String mockToken = jwtCreateService.issueToken(mockAppUser).get();

    DecodedJWT jwt = JWT.decode(mockToken);

    @Test
    void testJwtCreateServiceId() {
        assertEquals(mockAppUser.getId(), Integer.parseInt(jwt.getSubject()));
    }

    @Test
    void testJwtCreateServiceRole(){
        assertEquals(mockAppUser.getRole().toString(), jwt.getClaim("scp").asList(String.class).getFirst());
    }
}

