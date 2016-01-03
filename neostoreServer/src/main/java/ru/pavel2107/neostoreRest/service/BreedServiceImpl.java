package ru.pavel2107.neostoreRest.service;

/**
 * Created by lenovo on 27.12.2015.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pavel2107.neostoreRest.model.Breed;
import ru.pavel2107.neostoreRest.model.Pet;
import ru.pavel2107.neostoreRest.repository.BreedJpaRepository;
import ru.pavel2107.neostoreRest.repository.PetRepository;
import ru.pavel2107.neostoreRest.utils.LoggerWrapper;
import ru.pavel2107.neostoreRest.utils.NotFoundException;
import ru.pavel2107.neostoreRest.utils.WrongUserException;
import sun.rmi.runtime.Log;

import java.util.List;

//
// имплементация сервиса для пород животных
//
@Service( "breedService")
@Transactional( readOnly = true)
public class BreedServiceImpl implements BreedService {
    protected static final LoggerWrapper LOG = LoggerWrapper.get(UserServiceImpl.class);

    @Autowired
    BreedJpaRepository repository;

    @Override
    public Breed get(int id)  {
        LOG.info( "BREED. get. id =" + id);
        return repository.findOne( id);
    }

       @Override
    public List<Breed> getAll() {
        LOG.info( "BREED.getAll");
        return (List<Breed>) repository.findAll();
    }
}
