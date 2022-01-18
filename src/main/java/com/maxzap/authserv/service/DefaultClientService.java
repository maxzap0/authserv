package com.maxzap.authserv.service;

import com.maxzap.authserv.entity.ClientEntity;
import com.maxzap.authserv.exception.LoginException;
import com.maxzap.authserv.exception.RegistrationException;
import com.maxzap.authserv.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultClientService implements ClientService{

    private final ClientRepository clientRepository;

    @Override
    public void register(String clientId, String clientSecret) {
        if (clientRepository.findById(clientId).isPresent()) {
            throw new RegistrationException("client wtith id "+clientId+" already registred");
        }
        String hash = BCrypt.hashpw(clientSecret, BCrypt.gensalt());
        clientRepository.save(new ClientEntity(clientId, hash));
    }

    @Override
    public void checkCredentials(String clientId, String clientSecret) {
        var client = clientRepository.findById(clientId);
        if (client.isEmpty()) {
            throw new LoginException("client with id "+clientId+" not found");
        }
        ClientEntity clientEntity = client.get();

        if (!BCrypt.checkpw(clientSecret, clientEntity.getHash())) {
            throw new LoginException("Secret uncorrected");
        }
    }
}
