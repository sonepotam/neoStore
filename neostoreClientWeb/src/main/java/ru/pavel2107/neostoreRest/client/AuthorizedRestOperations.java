package ru.pavel2107.neostoreRest.client;

import org.springframework.web.client.RestOperations;

/**
 * Created by pavel2107 on 24.12.15.
 */
public interface AuthorizedRestOperations extends RestOperations{
    void setCredentials( String username, String password);
    }
