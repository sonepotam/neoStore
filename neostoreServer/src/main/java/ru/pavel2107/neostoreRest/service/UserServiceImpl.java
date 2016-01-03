package ru.pavel2107.neostoreRest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pavel2107.neostoreRest.LoggedUser;
import ru.pavel2107.neostoreRest.model.User;
import ru.pavel2107.neostoreRest.repository.UserRepository;
import ru.pavel2107.neostoreRest.utils.LoggerWrapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created by lenovo on 30.11.2015.
 */
//
// сервис для людей - имплементация
//
@Service("userService")
@Transactional( readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {
    protected static final LoggerWrapper LOG = LoggerWrapper.get(UserServiceImpl.class);

    @Autowired
    UserRepository repository;

    @Override
    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User get(int id)  {
        return repository.findOne( id);
    }

    @Override
    public User getByName(String name) {
        User user  = null;
        try {
            user = repository.findByUsername(name);
        }
        catch ( Exception e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        LOG.info( "Service.getAll");
        return (List<User>) repository.findAll();
    }

    //
    // увеличиваем количесто неудачных попыток логина
    //
    @Override
    @Transactional
    public void updateFailedAttemps( String username){
        LOG.info( "update attempts for user " + username);
        //
        // пробуем найти пользователя по имени
        //
        User user = getByName( username);
        if( user == null) return;
        //
        // определяем интервал до предыдущей попытки логина
        //
        LocalDateTime loginDate = user.getLastModified();
        if( loginDate == null) loginDate= LocalDateTime.now();
        long diff = ChronoUnit.MINUTES.between( LocalDateTime.now(), loginDate);
        //
        //  прошло меньше часа, увеличиваем кол-во попыток
        //  если больше часа, начинаем отсчет снова с первой попытки
        if( diff <= 60){
            user.setAttempts(user.getAttempts() + 1);
            if( user.getLastModified() == null)
                user.setLastModified(LocalDateTime.now());
        } else {
            user.setAttempts(1);
            user.setLastModified(LocalDateTime.now());
        }
    }

    //
    // сброс количества неудачных попыток логина.
    // вызывается при успешном логине
    //
    @Override
    @Transactional
    public void resetAttemps( String username){
        LOG.info( "reset attempts for user " + username);
        User user = getByName( username);

        user.setLastModified(null);
        user.setAttempts(0);
    }

    //
    // заодно заставим этот же сервис работать в качестве UserDetailService
    // нет смысла еще один сервис делать для реализации одного метода
    //
    @Override
    public LoggedUser loadUserByUsername(String name) throws UsernameNotFoundException {
        User u = repository.findByUsername( name);
        if (u == null) {
            throw new UsernameNotFoundException("User " + name + " is not found");
        }
        return new LoggedUser(u);
    }


}
