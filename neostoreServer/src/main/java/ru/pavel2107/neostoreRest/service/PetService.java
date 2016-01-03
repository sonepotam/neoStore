package ru.pavel2107.neostoreRest.service;

import ru.pavel2107.neostoreRest.model.Pet;
import ru.pavel2107.neostoreRest.model.User;

import java.util.List;

/**
 * Created by lenovo on 30.11.2015.
 */
//
// сервис для животных
//
public interface PetService {

    Pet save(Pet pet, int ownerId);

    void delete(int id, int wnerId);

    Pet get(int id, int petId);

    List<Pet> getAll(int ownerId);

}

