package com.myserver.springserver.services;

import com.myserver.springserver.exception.FilmAlreadyExistException;
import com.myserver.springserver.exception.FilmNotFoundException;
import com.myserver.springserver.model.Film;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FilmsService {
    List<Film> getFilms();

    Film getFilm(Long id) throws FilmNotFoundException;

    Film addFilm(Film film) throws FilmAlreadyExistException;

    Film updateFilm(Long idm, Film film) throws FilmNotFoundException;

    void deleteFilm(Long id) throws FilmNotFoundException;

    void deleteAllFilms();
}
