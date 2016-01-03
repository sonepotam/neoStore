package ru.pavel2107.neostoreRest.service;

import ru.pavel2107.neostoreRest.model.Breed;

import java.util.List;

/**
 * Created by lenovo on 27.12.2015.
 */
//
// сервис для пород животных. все по минимуму. читаем всех и одного по идентификатору
//
public interface BreedService {
    Breed get(int id);
    List<Breed> getAll();
}
