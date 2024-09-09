package com.myserver.springserver.services.implementation;

import com.myserver.springserver.exception.AlreadyExistException;
import com.myserver.springserver.exception.NotFoundException;
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
    public Film getFilm(Long id) throws NotFoundException {
        return filmRepo.findById(id).orElseThrow(() -> new NotFoundException("This film not exist"));
    }

    @Override
    public Film addFilm(Film film) throws AlreadyExistException {

        if (filmRepo.findByTitle(film.getTitle()).isPresent()) {
            throw new AlreadyExistException("This film already exist");
        }

        return filmRepo.save(film);
    }

    @Override
    public Film updateFilm(Long id, Film film) throws NotFoundException {
        Film updateFilm = filmRepo.findById(id).orElseThrow(() -> new NotFoundException("This film not exist"));

        updateFilm.setTitle(film.getTitle());
        updateFilm.setTime(film.getTime());
        return filmRepo.save(updateFilm);
    }

    @Override
    public void deleteFilm(Long id) throws NotFoundException {
        filmRepo.findById(id).orElseThrow(() -> new NotFoundException("This film not exist"));
        filmRepo.deleteById(id);
    }

    @Override
    public void deleteAllFilms() {
        filmRepo.deleteAll();
    }
}
