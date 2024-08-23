package com.myserver.springserver.services.impl;

import com.myserver.springserver.exception.FilmAlreadyExistException;
import com.myserver.springserver.exception.FilmNotFoundException;
import com.myserver.springserver.model.Film;
import com.myserver.springserver.repository.FilmRepo;
import com.myserver.springserver.services.FilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmsServiceImpl implements FilmsService {

    @Autowired
    private FilmRepo filmRepo;

    @Override
    public List<Film> getFilms() {
        return filmRepo.findAll();
    }

    @Override
    public Film getFilm(Long id) throws FilmNotFoundException {
        return filmRepo.findById(id).orElseThrow(() -> new FilmNotFoundException("This film not exist"));
    }

    @Override
    public Film addFilm(Film film) throws FilmAlreadyExistException {

        if (filmRepo.findByTitle(film.getTitle()) != null) {
            throw new FilmAlreadyExistException("This film already exist");
        }

        return filmRepo.save(film);
    }

    @Override
    public Film updateFilm(Long id, Film film) throws FilmNotFoundException {
        Film updateFilm = filmRepo.findById(id).orElseThrow(() -> new FilmNotFoundException("This film not exist"));

        updateFilm.setTitle(film.getTitle());
        updateFilm.setTime(film.getTime());
        return filmRepo.save(updateFilm);
    }

    @Override
    public void deleteFilm(Long id) throws FilmNotFoundException {
        filmRepo.findById(id).orElseThrow(() -> new FilmNotFoundException("This film not exist"));
        filmRepo.deleteById(id);
    }

    @Override
    public void deleteAllFilms() {
        filmRepo.deleteAll();
    }
}
