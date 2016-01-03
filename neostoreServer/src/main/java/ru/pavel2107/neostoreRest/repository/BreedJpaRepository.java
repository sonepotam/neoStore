package ru.pavel2107.neostoreRest.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pavel2107.neostoreRest.model.Breed;

/**
 * Created by lenovo on 27.12.2015.
 */
//
// а вот JPA репозиторий для пород животных
//
public interface BreedJpaRepository extends CrudRepository<Breed, Integer> {
}
