package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.application.DeleteUserInteractor;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeleteUserInteractorImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserInteractor sut;
}
