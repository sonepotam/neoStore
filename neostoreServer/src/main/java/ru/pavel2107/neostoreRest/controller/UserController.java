package ru.pavel2107.neostoreRest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.pavel2107.neostoreRest.LoggedUser;
import ru.pavel2107.neostoreRest.model.User;
import ru.pavel2107.neostoreRest.service.UserService;
import ru.pavel2107.neostoreRest.utils.*;

import javax.persistence.NoResultException;
import java.util.Collection;

/**
 * Created by lenovo on 09.12.2015.
 */

//
// CRUD-контроллер для пользователя
// без аутентификации можно только зарегистрироваться

@RestController
public class UserController
      {
    LoggerWrapper LOG = LoggerWrapper.get( UserController.class);

    @Autowired
    UserService service;

    @RequestMapping(value = UserControllerResources.CREATE, method = RequestMethod.POST,
            consumes= MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create( @RequestBody User user){
        String username = user.getUsername();
        String password = user.getPassword();
        LOG.info( "create user: " + username + "/" + password);
        user.setPassword(PasswordUtil.encode( user.getPassword()));
        user = service.save( user);
        return new ResponseEntity<>( user, HttpStatus.CREATED);
    }

    @RequestMapping(value = UserControllerResources.FIND, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User findOne( @PathVariable("username") String username){
        LOG.info( "searching name " + username);
        User user =  service.getByName(username);
        return user;
    }

    @RequestMapping(value = UserControllerResources.LOGIN, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User login( @PathVariable("username") String username){
      LOG.info( "logging in " + username);
      User user =  service.getByName(username);
      return user;
    }

    @RequestMapping(value = UserControllerResources.GET, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User get( @PathVariable("id") int id){
        LOG.info( "searching name " + id);
        User user = service.get( id);

        return user;
    }

    @RequestMapping(value = UserControllerResources.GET_ALL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<User> getAll(){
        LOG.info( "get all users");
        return service.getAll();
    }


}
