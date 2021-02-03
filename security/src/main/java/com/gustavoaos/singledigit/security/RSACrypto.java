package com.gustavoaos.singledigit.security;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RSACrypto {

    private static KeyPairGenerator keyPairGenerator = null;

    @PostConstruct
    private void init() {
        try {
            if (keyPairGenerator == null) {
                keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            }

            keyPairGenerator.initialize(2048);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public KeyPair getKeyPair() {
        return keyPairGenerator.genKeyPair();
    }

    public String encryptWithPublicKey(String text, PublicKey publicKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherContent = cipher.doFinal(text.getBytes());

        return Base64.getEncoder().encodeToString(cipherContent);
    }

    public String decryptWithPrivateKey(String text,PrivateKey privateKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] cipherContentBytes = Base64.getDecoder().decode(text.getBytes());
        byte[] decryptedContent = cipher.doFinal(cipherContentBytes);

        return new String(decryptedContent);
    }

    public String encodeKey(Key key) {
        byte[] keyBytes = key.getEncoded();

        return Base64.getEncoder().encodeToString(keyBytes);
    }

    public PublicKey decodePublicKey(String keyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.getDecoder().decode(keyStr);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePublic(spec);
    }

    public PrivateKey decodePrivateKey(String keyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.getDecoder().decode(keyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePrivate(keySpec);
    }

}
