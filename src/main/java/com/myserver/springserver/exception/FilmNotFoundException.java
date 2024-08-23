package com.myserver.springserver.exception;

public class FilmNotFoundException extends Exception {
    public FilmNotFoundException(String message) {
        super(message);
    }
}
