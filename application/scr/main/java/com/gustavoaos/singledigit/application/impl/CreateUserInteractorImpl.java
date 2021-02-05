package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.application.CreateUserInteractor;
import com.gustavoaos.singledigit.application.request.CreateUserRequest;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.User;
import com.gustavoaos.singledigit.domain.exception.CryptoException;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import com.gustavoaos.singledigit.security.RSACrypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

@Component
public class CreateUserInteractorImpl implements CreateUserInteractor {

    private final UserRepository userRepository;
    private final RSACrypto rsaCrypto;

    @Autowired
    public CreateUserInteractorImpl(
            UserRepository userRepository,
            RSACrypto rsaCrypto) {
        this.userRepository = userRepository;
        this.rsaCrypto = rsaCrypto;
    }

    @Override
    public UserResponse execute(CreateUserRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Missing argument of type CreateUserRequest");
        }
        try {
            User user = encryptRequest(request);
            User domainUser = userRepository.save(user);

            return UserResponse.from(domainUser);
        } catch (NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException err) {
            throw new CryptoException("user");
        }
    }

    private User encryptRequest(CreateUserRequest request)
            throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        KeyPair keyPair = rsaCrypto.getKeyPair();
        String publicKey = rsaCrypto.encodeKey(keyPair.getPublic());
        String privateKey = rsaCrypto.encodeKey(keyPair.getPrivate());

        request.setName(encrypt(request.getName(), keyPair.getPublic()));
        request.setEmail(encrypt(request.getEmail(), keyPair.getPublic()));

        return request.toDomain(publicKey, privateKey);
    }

    private String encrypt(String text, PublicKey publicKey)
            throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return rsaCrypto.encryptWithPublicKey(text, publicKey);
    }

}
