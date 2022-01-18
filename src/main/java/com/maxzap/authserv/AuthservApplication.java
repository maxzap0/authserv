package com.maxzap.authserv;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigInteger;
import java.security.SecureRandom;

@Log
@SpringBootApplication
public class AuthservApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthservApplication.class, args);
        log.info("<<<RunApp>>>");

    }
}
