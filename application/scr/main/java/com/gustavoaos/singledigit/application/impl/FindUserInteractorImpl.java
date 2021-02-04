package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.application.FindUserInteractor;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.User;
import com.gustavoaos.singledigit.domain.exception.CryptoException;
import com.gustavoaos.singledigit.domain.exception.NotFoundException;
import com.gustavoaos.singledigit.domain.exception.WrongKeyException;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import com.gustavoaos.singledigit.security.RSACrypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

@Component
public class FindUserInteractorImpl implements FindUserInteractor {

    private final UserRepository userRepository;
    private final RSACrypto rsaCrypto;

    @Autowired
    public FindUserInteractorImpl(
            UserRepository userRepository,
            RSACrypto rsaCrypto) {
        this.userRepository = userRepository;
        this.rsaCrypto = rsaCrypto;
    }

    @Override
    public UserResponse execute(String id) {
        User user = userRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("user", id));

        return UserResponse.from(user);
    }

    @Override
    public UserResponse execute(String id, String providedKey) {
        try {
            User user = userRepository
                    .findById(UUID.fromString(id))
                    .orElseThrow(() -> new NotFoundException("user", id));

            if (!providedKey.equals(user.getPublicKey())) {
                throw new WrongKeyException("user", "id");
            }

            PrivateKey privateKey = rsaCrypto.decodePrivateKey(user.getPrivateKey());
            String decryptedName = rsaCrypto.decryptWithPrivateKey(user.getName(), privateKey);
            String decryptedEmail = rsaCrypto.decryptWithPrivateKey(user.getEmail(), privateKey);

            UserResponse userResponse = UserResponse.from(user);
            userResponse.setName(decryptedName);
            userResponse.setEmail(decryptedEmail);

            return userResponse;
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException err) {
            throw new CryptoException("user");
        }
    }

}
