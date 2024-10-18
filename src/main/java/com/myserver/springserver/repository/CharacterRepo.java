package com.myserver.springserver.repository;

import com.myserver.springserver.model.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterRepo extends JpaRepository<CharacterEntity, Long> {
    Optional<CharacterEntity> findByAlias(String title);
}
