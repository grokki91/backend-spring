package com.myserver.springserver.services;

import com.myserver.springserver.exception.AlreadyExistException;
import com.myserver.springserver.exception.NotFoundException;
import com.myserver.springserver.model.CharacterEntity;

import java.util.List;

public interface CharacterService {
    List<CharacterEntity> getCharacters();

    CharacterEntity getCharacter(Long id) throws NotFoundException;

    void addCharacter(CharacterEntity character) throws AlreadyExistException;

    void updateCharacter(Long idm, CharacterEntity film) throws NotFoundException;

    void deleteCharacter(Long id) throws NotFoundException;

    void deleteAllCharacters();
}
