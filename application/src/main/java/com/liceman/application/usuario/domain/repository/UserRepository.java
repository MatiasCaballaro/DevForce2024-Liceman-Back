package com.liceman.application.usuario.domain.repository;

import com.liceman.application.usuario.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail (String email);

    boolean existsByEmail (String email);

}
