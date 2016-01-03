package ru.pavel2107.neostoreRest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.pavel2107.neostoreRest.model.User;
import ru.pavel2107.neostoreRest.utils.LoggerWrapper;
import ru.pavel2107.neostoreRest.utils.UserTo;
import ru.pavel2107.neostoreRest.utils.UserUtil;

import java.util.HashSet;

import static java.util.Objects.requireNonNull;

//
// залогированный пользователь
// к нему прикручиаем DTO и делаем его из спрингового класса User
// теперь все данные я получу и контекста
//
public class LoggedUser extends org.springframework.security.core.userdetails.User {
    private static final LoggerWrapper LOG = LoggerWrapper.get( LoggedUser.class);

    private final UserTo userTo;

    public LoggedUser(User user) {
        super(user.getUsername(), user.getPassword(),  new HashSet<GrantedAuthority>());
        this.userTo = UserUtil.asTo( user);
    }

    public static LoggedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object user = auth.getPrincipal();
        LOG.info( "SafeGet.user =" + user);
        return (user instanceof LoggedUser) ? (LoggedUser) user : null;
    }

    public static LoggedUser get() {
        LoggedUser user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }

    public UserTo getUserTo(){ return userTo;}

    public static int id() {
        return get().userTo.getId();
    }


}

