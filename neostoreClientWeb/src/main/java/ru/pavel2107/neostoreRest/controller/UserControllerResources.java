package ru.pavel2107.neostoreRest.controller;

/**
 * Created by lenovo on 23.12.2015.
 */
public interface UserControllerResources {
    //
    // методы под /rest/anonym/ - доступны всем
    // методы под /rest/auth/   - доступны аутентифицированным пользователям
    //
    String CREATE = "/rest/anonym/users/create";
    String FIND   = "/rest/anonym/users/find/{username}";
    String GET    = "/rest/auth/users/get/{id}";
    String GET_ALL = "/rest/auth/users/list";
    String LOGIN   = "/rest/auth/users/login/{username}";


    String ERROR = "/rest/anonym/users/error";
}
