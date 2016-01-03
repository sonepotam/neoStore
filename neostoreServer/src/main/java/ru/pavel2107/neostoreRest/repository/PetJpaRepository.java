package ru.pavel2107.neostoreRest.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pavel2107.neostoreRest.model.Pet;

/**
 * Created by pavel2107 on 21.12.15.
 */
//
// JPA репозиторий для животных
//
public interface PetJpaRepository extends CrudRepository <Pet, Integer> {

}
