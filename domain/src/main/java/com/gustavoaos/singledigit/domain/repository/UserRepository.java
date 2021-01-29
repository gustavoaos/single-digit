package com.gustavoaos.singledigit.domain.repository;

import com.gustavoaos.singledigit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Override
    <S extends User> S save(S s);

    @Override
    Optional<User> findById(UUID uuid);
}
