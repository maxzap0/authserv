package com.maxzap.authserv.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class DefaultTokenService implements TokenService {

    @Value("${auth.jwt.secret}")
    private String secretKey;

    @Override
    public String generatedToken(String clientId) {
        /*Формируем алгоритм*/
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        /*Устанавливаем текущее время и время жизни токена*/
        Instant now = Instant.now();
        Instant exp = now.plus(10, ChronoUnit.MINUTES);

        return JWT.create()
                .withIssuer("auth-service")     //Издатель токена
                .withAudience("bookStore")      //сервис для использования токена
                .withSubject(clientId)          //идентификатор клиента
                .withIssuedAt(Date.from(now))   //время начала токена
                .withExpiresAt(Date.from(exp))  //время окончания токена
                .sign(algorithm);
    }
}
