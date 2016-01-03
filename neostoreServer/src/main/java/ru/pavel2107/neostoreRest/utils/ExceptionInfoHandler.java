package ru.pavel2107.neostoreRest.utils;

/**
 * Created by pavel2107 on 16.12.15.
 * Интерфейс для описания ошибок.
 * Проект тестовый, поэтому в классе описываем только три вида ошибок
 *
 */

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

public interface ExceptionInfoHandler {
    LoggerWrapper LOG = LoggerWrapper.get(ExceptionInfoHandler.class);


    @ExceptionHandler(Exception.class)
    default ErrorInfo prepareErrorInfo(HttpServletRequest request, Exception e) {
        LOG.error( "errorInfo:" + e.getMessage());
        ErrorInfo error = new ErrorInfo(HttpStatus.BAD_REQUEST.value(),e.getLocalizedMessage());
        return error;
    }


    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found exception")
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE)
    default ErrorInfo handleError(HttpServletRequest req, ChangeSetPersister.NotFoundException e) {
        return LOG.getErrorInfo(req.getRequestURL(), e, 1);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "user not found")
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE+1)
    default ErrorInfo notUserFoundError(HttpServletRequest req, ChangeSetPersister.NotFoundException e) {
        return LOG.getErrorInfo(req.getRequestURL(), e, 1);
    }


    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "already exists")  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE + 2)
    default ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return LOG.getErrorInfo(req.getRequestURL(), e, 2);
    }


    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "sometimes blue")
    @ExceptionHandler(NoResultException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE+3)
    default ErrorInfo handleError(HttpServletRequest req, NoResultException e) {
        return LOG.getErrorInfo(req.getRequestURL(), e, 3);
    }


    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "not found")
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE+4)
    default ErrorInfo NotFoundException(HttpServletRequest req, NotFoundException e) {
        return LOG.getErrorInfo(req.getRequestURL(), e, 4);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "wai-wai..")
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @Order(Ordered.LOWEST_PRECEDENCE)
    default ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return LOG.getErrorInfo(req.getRequestURL(), e, 999);
    }


}