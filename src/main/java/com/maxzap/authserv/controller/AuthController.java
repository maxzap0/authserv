package com.maxzap.authserv.controller;

import com.maxzap.authserv.exception.LoginException;
import com.maxzap.authserv.exception.RegistrationException;
import com.maxzap.authserv.model.ErrorResponse;
import com.maxzap.authserv.model.TokerResponse;
import com.maxzap.authserv.model.User;
import com.maxzap.authserv.service.ClientService;
import com.maxzap.authserv.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ClientService clientService;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<String> registerUser (@RequestBody User user) {
        clientService.register(user.getClientId(), user.getClientSecret());
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/token")
    public TokerResponse getToken(@RequestBody User user) {
        clientService.checkCredentials(user.getClientId(), user.getClientSecret());
        return new TokerResponse(tokenService.generatedToken(user.getClientId()));
    }

    //При возникновении ошибки вернём созданный объект ErrorResponse
    @ExceptionHandler({RegistrationException.class, LoginException.class})
    public ResponseEntity<ErrorResponse> handlerUserRegistrationException(RuntimeException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }
}
