package com.gustavoaos.singledigit.infrastructure.repository;

import com.gustavoaos.singledigit.domain.User;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaUserRepository  implements UserRepository {

    @Override
    public User create(User user) {
        return null;
    }
}
