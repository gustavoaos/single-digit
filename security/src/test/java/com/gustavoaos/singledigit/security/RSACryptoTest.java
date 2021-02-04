package com.gustavoaos.singledigit.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import java.security.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RSACryptoTest {

    @Autowired
    private RSACrypto sut;

    KeyPair keyPair;

    @BeforeEach
    void initEach() {
        keyPair = sut.getKeyPair();
    }

    @Test
    @Description("Should decrypt encrypted text")
    void shouldDecryptEncryptedText() throws Exception {
        String text = "Joe Doe";
        String encrypted = sut.encryptWithPublicKey(text, keyPair.getPublic());

        assertThat(sut.decryptWithPrivateKey(encrypted, keyPair.getPrivate())).isEqualTo("Joe Doe");
    }

    @Test
    @Description("Should decode public key")
    void shouldDecodePublicKey() throws Exception {
        String encodedPublicKey = sut.encodeKey(keyPair.getPublic());

        assertThat(sut.decodePublicKey(encodedPublicKey)).isEqualTo(keyPair.getPublic());
    }

    @Test
    @Description("Should decode private key")
    void shouldDecodePrivateKey() throws Exception {
        String encodedPrivateKey = sut.encodeKey(keyPair.getPrivate());

        assertThat(sut.decodePrivateKey(encodedPrivateKey)).isEqualTo(keyPair.getPrivate());
    }

}
