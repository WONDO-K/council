package com.wondo.council.repository;

import com.wondo.council.domain.User;
import com.wondo.council.domain.enums.UserIsMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String userid);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmail(String email);


}
