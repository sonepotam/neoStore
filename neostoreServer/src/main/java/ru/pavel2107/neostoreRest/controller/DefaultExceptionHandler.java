package ru.pavel2107.neostoreRest.controller;

/**
 * Created by lenovo on 26.12.2015.
 */

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.pavel2107.neostoreRest.utils.ErrorInfo;
import ru.pavel2107.neostoreRest.utils.NotFoundException;
import ru.pavel2107.neostoreRest.utils.WrongUserException;

//
// Контроллер уровня приложения. Обрабатывает предопределенные сообщения об ошибках.
// В нем не зависимо от возникающей ошибки - пакуем ошибку в json и отправляем клиенту
// никакой дополнительной обработки не произодится.
//
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ErrorInfo.class })
    protected ResponseEntity<Object> handleErrorInfo(RuntimeException e, WebRequest request) {
        ErrorInfo errorInfo = (ErrorInfo) e;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, errorInfo, headers, HttpStatus.NOT_IMPLEMENTED, request);
    }

    @ExceptionHandler({ NotFoundException.class })
    protected ResponseEntity<Object> handleNotFoundException(RuntimeException e, WebRequest request) {
        ErrorInfo errorInfo = new ErrorInfo( 100, e.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, errorInfo, headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ WrongUserException.class })
    protected ResponseEntity<Object> handleWrongUserException(RuntimeException e, WebRequest request) {
        ErrorInfo errorInfo = new ErrorInfo( 200, e.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, errorInfo, headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    protected ResponseEntity<Object> handleDataIntegrityViolationException(RuntimeException e, WebRequest request) {
        ErrorInfo errorInfo = new ErrorInfo( 300, e.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, errorInfo, headers, HttpStatus.CONFLICT, request);
    }


    @ExceptionHandler({RuntimeException.class })
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException e, WebRequest request) {
        ErrorInfo errorInfo = new ErrorInfo( 900, e.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, errorInfo, headers, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({Exception.class })
    protected ResponseEntity<Object> handleException(RuntimeException e, WebRequest request) {
        ErrorInfo errorInfo = new ErrorInfo( 990, e.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, errorInfo, headers, HttpStatus.CONFLICT, request);
    }

}
