package com.myserver.springserver.repository;

import com.myserver.springserver.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmRepo extends JpaRepository<Film, Long> {
    Optional<Film> findByTitle(String title);
}
