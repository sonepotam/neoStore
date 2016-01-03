package ru.pavel2107.neostoreRest.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import ru.pavel2107.neostoreRest.model.User;
import ru.pavel2107.neostoreRest.service.UserService;
import ru.pavel2107.neostoreRest.utils.LoggerWrapper;
import ru.pavel2107.neostoreRest.utils.WrongUserException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by lenovo on 19.12.2015.
 *
 */
//
// провайдер для контроля количества логинов пользователей
//
//
//
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {
    private final static LoggerWrapper LOG = LoggerWrapper.get( LimitLoginAuthenticationProvider.class);

    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {
            //
            // читаем информацию о пользователе
            //
            LOG.info("начинаем аутентификацию " + authentication.getName());
            User user = userService.getByName( authentication.getName());
            if( user != null){
                 //
                 // если количество попыток исчерпано, и прошло менее 60 минут от последнего логина
                 // сразу бросаем исключение userLocked. шансов залогииться не даем
                 //
                LocalDateTime loginDate = user.getLastModified();
                if( loginDate == null) loginDate= LocalDateTime.now();
                long diff = ChronoUnit.MINUTES.between( loginDate, LocalDateTime.now());
                if( user.getAttempts() >= 10 && diff < 60){
                  LOG.info("Locked exception");
                 throw new LockedException("user " + user.getUsername() + " locked");
                };
            }
            //
            // проверяем учетные данные
            //
            Authentication auth = super.authenticate(authentication);
            //
            // логин прошел, значит можно сбрасывать счетчик
            //
            userService.resetAttemps( auth.getName());
            LOG.info("успешно !");
            return auth;

        } catch (BadCredentialsException e) {
            LOG.info("НЕ успешно. ");
            //
            // логин не прошел - увеличиваем счетчик
            //
            userService.updateFailedAttemps( authentication.getName());
            User user = userService.getByName( authentication.getName());
            if( user != null){
                LOG.info( "использовано " + user.getAttempts() + " попыток");
                if( user.getAttempts() >= 10) {
                    LOG.info("Locked exception");
                    throw new LockedException("user " + user.getUsername() + " locked");
                }
            }

            throw e;

        } catch (LockedException e) {
            throw new LockedException(e.getMessage());
        }

    }

    public UserService getUserDetailsDao() {
        return userService;
    }

    public void setUserDetailsDao( UserService userService) {
        this.userService = userService;
    }

}