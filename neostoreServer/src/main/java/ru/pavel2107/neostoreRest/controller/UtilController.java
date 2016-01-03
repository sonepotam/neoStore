package ru.pavel2107.neostoreRest.controller;

/**
 * Created by lenovo on 27.12.2015.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.pavel2107.neostoreRest.model.Breed;
import ru.pavel2107.neostoreRest.service.BreedService;
import ru.pavel2107.neostoreRest.utils.*;

import java.util.List;

//
// утилитный контроллер для работы со справочниками, если бы справочников было бы много, сех бы сюда и завел
//
@RestController
public class UtilController {
    LoggerWrapper LOG = LoggerWrapper.get( UtilController.class);

    @Autowired
    BreedService breedService;


    @RequestMapping(value = UtilControllerResources.BREED_GET, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Breed breedGet( @PathVariable("id") int id){
        LOG.info( "searching name " + id);
        Breed breed = breedService.get( id);

        return breed;
    }

    @RequestMapping(value = UtilControllerResources.BREED_GET_ALL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Breed> breedGetAll(){
        LOG.info( "get all users");
        return breedService.getAll();
    }
}
