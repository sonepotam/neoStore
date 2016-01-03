package ru.pavel2107.neostoreRest.service;

import ru.pavel2107.neostoreRest.model.User;

import java.util.List;

/**
 * Created by lenovo on 30.11.2015.
 */
//
// сервис для пользователей
//
public interface UserService {

    User save(User user);

    User get(int id);

    User getByName(String name) ;

    List<User> getAll();

    void updateFailedAttemps( String username);

    void resetAttemps( String username);

}

