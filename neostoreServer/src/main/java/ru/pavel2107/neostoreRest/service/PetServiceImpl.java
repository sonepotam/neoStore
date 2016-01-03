package ru.pavel2107.neostoreRest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pavel2107.neostoreRest.model.Pet;
import ru.pavel2107.neostoreRest.repository.PetRepository;
import ru.pavel2107.neostoreRest.utils.LoggerWrapper;
import ru.pavel2107.neostoreRest.utils.NotFoundException;
import ru.pavel2107.neostoreRest.utils.WrongUserException;

import java.util.List;

/**
 * Created by lenovo on 30.11.2015.
 */

//
// Сервис для животных.
// 1. Позволяеи получить информацию только по переданному идентифиатору владельца
//
//
//
@Service( "petService")
@Transactional( readOnly = true)
public class PetServiceImpl implements PetService {
    private static final LoggerWrapper LOG = LoggerWrapper.get( PetServiceImpl.class);

    @Autowired
    PetRepository repository;

    private void makeChecks(Pet pet, int ownerId){
        if( pet.getUser() == null)
            throw new WrongUserException( "У животного " + pet.getId() + " нет владельца.");
        if( pet.getUser().getId() != ownerId)
            throw new WrongUserException( "Животное " + pet.getId() + " принадлежит другому владельцу");
    }

    @Override
    @Transactional
    public Pet save(Pet pet, int ownerId) {
        LOG.info( "сохраняем животное " + pet + ", владелец=" + ownerId);
        makeChecks(pet, ownerId);
        pet = repository.save( pet);
        return pet;
    }

    @Override
    @Transactional
    public void delete(int id, int ownerId)  {
        Pet pet = repository.findOne( id);
        LOG.info( "удаляем животное " + pet + ", владелец=" + ownerId);
        if( pet == null) throw new NotFoundException( "Животное " + id + " не найдено");
        makeChecks(pet, ownerId);
        repository.delete( id);
    }

    @Override
    public Pet get(int id, int ownerId)   {
        Pet pet = repository.findOne( id);
        LOG.info( "получаем животное " + pet + ", владелец=" + ownerId);
        if( pet == null) throw new NotFoundException( "Животное " + id + " не найдено");
        makeChecks(pet, ownerId);
        return pet;
    }

    @Override
    public List<Pet> getAll(int ownerId) {
        LOG.info( "получаем всех животных, владелец=" + ownerId);
        return repository.findByUserId( ownerId);
    }

}
