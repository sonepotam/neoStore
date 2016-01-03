package ru.pavel2107.neostoreRest.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.pavel2107.neostoreRest.service.PetService;
import ru.pavel2107.neostoreRest.service.UserService;

import static org.junit.Assert.*;

/**
 * Created by lenovo on 26.12.2015.
 */
public class PetControllerTest {


    ConfigurableApplicationContext appCtx;

    PetService petService;

    @Before
    public void setUp() throws Exception {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml","spring/spring-db.xml");

        for( String beanName : appCtx.getBeanDefinitionNames()) {
            System.out.println( beanName);
        }
        petService  = (PetService) appCtx.getBean( "petService");

    }

    @After
    public void tearDown() throws Exception {
        appCtx.close();
    }
    @Test
    public void testSave() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testGet() throws Exception {

    }

    @Test
    public void testGetAll() throws Exception {

    }
}