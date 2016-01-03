package ru.pavel2107.neostoreRest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.pavel2107.neostoreRest.model.User;

/**
 * GKislin
 * 06.03.2015.
 */
public class LoggedUser {


    protected int id = -1;
    protected boolean enabled = true;

    public int getId() {
        return id;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }



    public boolean isEnabled(){ return enabled;}

    public static int id() {
        return get().id;
    }
    public static int setId(int id) {
        get().id = id;
        return get().id;

    }
    public LoggedUser( User user){
        this.id = user.getId();
        this.enabled = true;
    }

    public LoggedUser(){}

    public static LoggedUser safeGet(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if( auth == null){return null; }
        Object user = auth.getPrincipal();
        return ( user instanceof LoggedUser) ? (LoggedUser) user: null;
    }

    private static LoggedUser LOGGED_USER = new LoggedUser();

    private static LoggedUser get(){ return LOGGED_USER; };



}
