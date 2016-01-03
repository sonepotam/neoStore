package ru.pavel2107.neostoreRest.utils;

/**
 * Created by lenovo on 26.12.2015.
 */
public class WrongUserException extends RuntimeException {
    public WrongUserException( String message){
        super( message);
    }
}
