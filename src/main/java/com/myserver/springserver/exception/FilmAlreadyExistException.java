package com.myserver.springserver.exception;

public class FilmAlreadyExistException extends Exception {
    public FilmAlreadyExistException(String message) {
        super(message);
    }
}
