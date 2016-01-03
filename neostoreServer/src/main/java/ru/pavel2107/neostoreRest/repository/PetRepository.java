package ru.pavel2107.neostoreRest.repository;

import ru.pavel2107.neostoreRest.model.Pet;
import ru.pavel2107.neostoreRest.model.User;

import java.util.List;

/**
 * Created by pavel2107 on 21.12.15.
 */
//
// кастомизиованный JPA репозиторий для животных
//
public interface PetRepository extends PetJpaRepository {
    //
    // поиск животного по номеру владельца. кода нет. магия спринга :)
    //
    List<Pet> findByUserId( Integer id);
}
