package com.myserver.springserver.repository;

import com.myserver.springserver.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByUsername(String email);

    Optional<MyUser> findByEmail(String email);
}
