package com.myserver.springserver.services.implementation;

import com.myserver.springserver.exception.AlreadyExistException;
import com.myserver.springserver.exception.NotFoundException;
import com.myserver.springserver.model.CharacterEntity;
import com.myserver.springserver.repository.CharacterRepo;
import com.myserver.springserver.services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CharacterServiceImpl implements CharacterService {

    private static final String HERO_NOT_FOUND = "The character with ID=%s does not exist";
    private static final String HERO_FOUND = "The character '%s' already exists";

    @Autowired
    private CharacterRepo characterRepo;

    @Override
    public List<CharacterEntity> getCharacters() {
        return characterRepo.findAll();
    }

    @Override
    public CharacterEntity getCharacter(Long id) throws NotFoundException {
        return characterRepo.findById(id).orElseThrow(() -> new NotFoundException(String.format(HERO_NOT_FOUND, id)));
    }

    @Override
    public void addCharacter(CharacterEntity character) throws AlreadyExistException {
        if (characterRepo.findByAlias(character.getAlias()).isPresent()) {
            throw new AlreadyExistException(String.format(HERO_FOUND, character.getAlias()));
        }

        characterRepo.save(character);
    }

    @Override
    public void updateCharacter(Long id, CharacterEntity character) throws NotFoundException {
        CharacterEntity updateHero = this.getCharacter(id);

        if (character.getAlias() != null) updateHero.setAlias(character.getAlias());
        if (character.getFull_name() != null) updateHero.setFull_name(character.getFull_name());
        if (character.getAlignment() != null) updateHero.setAlignment(character.getAlignment());
        if (character.getAbilities() != null) updateHero.setAbilities(character.getAbilities());
        if (character.getAge() != null) updateHero.setAge(character.getAge());
        if (character.getTeam() != null) updateHero.setTeam(character.getTeam());
        updateHero.setUpdated(LocalDateTime.now());

        characterRepo.save(updateHero);
    }

    @Override
    public void deleteCharacter(Long id) throws NotFoundException {
        characterRepo.findById(id).orElseThrow(() -> new NotFoundException(String.format(HERO_NOT_FOUND, id)));
        characterRepo.deleteById(id);
    }

    @Override
    public void deleteAllCharacters() {
        characterRepo.deleteAll();
    }
}
