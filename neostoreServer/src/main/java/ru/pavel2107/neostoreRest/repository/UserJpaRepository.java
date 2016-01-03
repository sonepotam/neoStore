package ru.pavel2107.neostoreRest.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pavel2107.neostoreRest.model.User;

/**
 * Created by pavel2107 on 21.12.15.
 */
//
// JPA репозиторий для пользователей
//
public interface UserJpaRepository extends CrudRepository<User, Integer> {

}
