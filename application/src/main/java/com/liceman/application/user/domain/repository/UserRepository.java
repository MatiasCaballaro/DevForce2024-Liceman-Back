package com.liceman.application.user.domain.repository;

import com.liceman.application.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {

    Optional<User> findByEmail (String email);

    boolean existsByEmail (String email);

}
