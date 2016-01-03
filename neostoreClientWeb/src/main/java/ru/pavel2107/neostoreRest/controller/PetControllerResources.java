package ru.pavel2107.neostoreRest.controller;

/**
 * Created by lenovo on 23.12.2015.
 */
public interface PetControllerResources {
    //
    // методы под /rest/anonym/ - доступны всем
    // методы под /rest/auth/   - доступны аутентифицированным пользователям
    //
   String SAVE    = "/rest/auth/pets/save";
   String DELETE  = "/rest/auth/pets/delete/{id}";
   String GET     = "/rest/auth/pets/get/{id}";
   String GET_ALL = "/rest/auth/pets/list";
}
