package ru.pavel2107.neostoreRest.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.pavel2107.neostoreRest.model.Breed;
import ru.pavel2107.neostoreRest.service.BreedService;
import ru.pavel2107.neostoreRest.service.PetService;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lenovo on 27.12.2015.
 */
public class UtilControllerTest {


    ConfigurableApplicationContext appCtx;

    BreedService breedService;

    @Before
    public void setUp() throws Exception {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml","spring/spring-db.xml");

        for( String beanName : appCtx.getBeanDefinitionNames()) {
            System.out.println( beanName);
        }
        breedService  = (BreedService) appCtx.getBean( "breedService");

    }

    @After
    public void tearDown() throws Exception {
        appCtx.close();
    }
    @Test
    public void testGet() throws Exception {
        Breed breed =  breedService.get(20);
        System.out.println( breed.getName());
    }

    @Test
    public void testGetAll() throws Exception {
        List list = breedService.getAll();
        System.out.println( list.toArray());
    }
}