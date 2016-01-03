package ru.pavel2107.neostoreRest.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.pavel2107.neostoreRest.model.Pet;
import ru.pavel2107.neostoreRest.model.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by pavel2107 on 21.12.15.
 */
public class UserServiceImplTest {

    ConfigurableApplicationContext appCtx;
    UserService userService;

    PetService petService;

    @Before
    public void setUp() throws Exception {
       appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml","spring/spring-db.xml");

       for( String beanName : appCtx.getBeanDefinitionNames()) {
            System.out.println( beanName);
        }
       userService = (UserService)appCtx.getBean( "userService");
       petService  = (PetService) appCtx.getBean( "petService");

    }

    @After
    public void tearDown() throws Exception {
       appCtx.close();
    }

    @Test
    public void testSave() throws Exception {
        User user = new User( null, "alex", "alex", LocalDateTime.now(), 0);
        userService.save( user);
        user = userService.getByName( "alex");
    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testGet() throws Exception {
        User user = userService.get( 1111111111);
        System.out.println( user);
    }

    @Test
    public void testGetByName() throws Exception {
       User user = userService.getByName( "alex");
        System.out.println( user);

        if( user != null) {
            List<Pet> pets = petService.getAll(user.getId());
            System.out.println(pets);
        }
    }

    @Test
    public void testLogin() throws Exception {

    }

    @Test
    public void testGetAll() throws Exception {
       List<User> list = userService.getAll();
       System.out.println( list);
    }
}