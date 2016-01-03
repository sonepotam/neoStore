package ru.pavel2107.neostoreRest.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by lenovo on 28.12.2015.
 */
//
// ошибки в процессе логина не ловятся ControllerAdvice, поэтому на клиента поедет страничка с кодом 404
// ну а нам нужен JSON - вот и сделаем JSON
//
public class Resp401BasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    LoggerWrapper LOG = LoggerWrapper.get( Resp401BasicAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        if( authException instanceof InsufficientAuthenticationException) {
 //           return;
 //       }
        response.addHeader("Access-Control-Allow-Origin", "null");
        response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
        response.addHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        PrintWriter writer = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        ErrorInfo errorInfo = new ErrorInfo( HttpServletResponse.SC_UNAUTHORIZED, authException.getLocalizedMessage());
        String jsonError = mapper.writeValueAsString( errorInfo);
        writer.println( jsonError);
        LOG.info( "result = " +jsonError);
    }
}
