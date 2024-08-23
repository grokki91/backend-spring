package com.myserver.springserver.repository;

import com.myserver.springserver.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepo extends JpaRepository<Film, Long> {
    Film findByTitle(String title);
}
