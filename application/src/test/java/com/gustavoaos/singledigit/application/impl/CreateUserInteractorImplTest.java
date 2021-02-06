package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.application.request.CreateUserRequest;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.User;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import com.gustavoaos.singledigit.security.RSACrypto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CreateUserInteractorImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RSACrypto rsaCrypto;

    @InjectMocks
    private CreateUserInteractorImpl sut;

    private UUID uuid;
    private String name;
    private String email;
    private UserResponse response;
    private static KeyPairGenerator keyPairGenerator;
    private KeyPair keyPair;

    @BeforeAll
    static void setUp() throws NoSuchAlgorithmException {
        keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
    }

    @BeforeEach
    void initEach() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        uuid = UUID.randomUUID();
        name = "Joe Doe";
        email = "joe@doe.com";
        keyPair = keyPairGenerator.genKeyPair();

        User user = User.builder().uuid(uuid).name(name).email(email).build();
        response = UserResponse.builder().id(uuid.toString()).name(name).email(email).build();

        when(userRepository.save(any())).thenReturn(user);
        when(rsaCrypto.getKeyPair()).thenReturn(keyPair);
        when(rsaCrypto.encryptWithPublicKey(any(), any())).thenReturn("encrypted_string");

        // Mockito does not support mocking static methods
        // when(UserResponse.from(user)).thenReturn(response);
    }

    @Test
    @Description("Should return an UserResponse when valid UserRequest is provided")
    void shouldReturnAnUserResponseWhenValidUserRequestIsProvided() {
        CreateUserRequest request = CreateUserRequest.builder().name(name).email(email).build();

        assertThat(sut.execute(request)).isEqualTo(response);
    }

    @Test
    @Description("Should throw an NullPointerException when request is null")
    void shouldThrowNullPointerExceptionWhenRequestIsNull() {
        assertThatThrownBy(() -> sut.execute(null))
                .isInstanceOf(NullPointerException.class);
    }

}
