package ru.pavel2107.neostoreRest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.pavel2107.neostoreRest.LoggedUser;
import ru.pavel2107.neostoreRest.model.Pet;
import ru.pavel2107.neostoreRest.service.PetService;
import ru.pavel2107.neostoreRest.utils.LoggerWrapper;

import java.util.List;

/**
 * Created by lenovo on 01.12.2015.
 */

//
// CRUD-контроллер для животных, Url для доступа к данным вынесены в отдельный интерфейс
//
@RestController
public class PetController  {
    LoggerWrapper LOG = LoggerWrapper.get( PetController.class);
    @Autowired
    PetService service;

   @RequestMapping(value = PetControllerResources.SAVE, method = RequestMethod.POST,
                   produces = MediaType.APPLICATION_JSON_VALUE,
                   consumes = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
    public Pet save(@RequestBody  Pet pet) {
        return service.save( pet, LoggedUser.id());
    }

    private int getLoggedUserID(){
        int result = -1;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Object user = auth.getPrincipal();
        LOG.info( "getLoggedUser.user =" + user);
        LoggedUser u = (user instanceof LoggedUser) ? (LoggedUser) user : null;
        if( u != null) {
            LOG.info("getLoggedUser.LoggedUser =" + u);
            LOG.info("getLoggedUser.id=" + u.getUserTo().getId());
            result = u.getUserTo().getId();
        }
        return result;
    }

    @RequestMapping(value = PetControllerResources.DELETE, method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("id") int id)   {
        LOG.info("delete PET.id =" + id);
        getLoggedUserID();
        service.delete(id, LoggedUser.id());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = PetControllerResources.GET, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Pet get(@PathVariable("id") int id)   {
        LOG.info("get PET.id =" + id);
        getLoggedUserID();
        return service.get(id, LoggedUser.id());
    }

    @RequestMapping(value = PetControllerResources.GET_ALL, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Pet> getAll() {
        LOG.info("getAll pets");
        getLoggedUserID();
        return service.getAll( LoggedUser.id() );
    }
}
