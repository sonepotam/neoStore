package ru.pavel2107.neostoreRest.repository;

import ru.pavel2107.neostoreRest.model.User;

/**
 * Created by pavel2107 on 21.12.15.
 */
//
// кастомизированный JPA репозиторий для пользователей
//
public interface UserRepository extends UserJpaRepository{
    //
    // поиск пользователя по имени. кода нет. волшебство
    //
    User findByUsername(String name);
}
