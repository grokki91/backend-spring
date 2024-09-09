package com.myserver.springserver.services;

import com.myserver.springserver.exception.AlreadyExistException;
import com.myserver.springserver.exception.NotFoundException;
import com.myserver.springserver.model.Film;

import java.util.List;

public interface FilmsService {
    List<Film> getFilms();

    Film getFilm(Long id) throws NotFoundException;

    Film addFilm(Film film) throws AlreadyExistException;

    Film updateFilm(Long idm, Film film) throws NotFoundException;

    void deleteFilm(Long id) throws NotFoundException;

    void deleteAllFilms();
}
