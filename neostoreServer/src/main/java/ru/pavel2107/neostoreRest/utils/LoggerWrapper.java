package ru.pavel2107.neostoreRest.utils;

/*
* Заглушка для логгирования
*
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggerWrapper {

    private Logger logger;

    public LoggerWrapper(Logger logger) {
        this.logger = logger;
    }

    public static LoggerWrapper get(Class aClass) {
        return new LoggerWrapper(LoggerFactory.getLogger(aClass));
    }

    public void debug(String msg) {
        logger.debug(msg);
    }

    public void info(String msg, Object... arguments) {
        logger.info(msg, arguments);
        System.out.println( msg);
    }

    public void warn(String msg) {
        logger.warn(msg);
    }

    public void warn(String msg, Throwable t) {
        logger.warn(msg, t);
    }

    public void error(String msg) {
        logger.error(msg);
    }

    public void error(String msg, Throwable t) {
        logger.error(msg, t);
    }

    public boolean isDebug() {
        return logger.isDebugEnabled();
    }

    public IllegalStateException getIllegalStateException(String msg) {
        return getIllegalStateException(msg, null);
    }

    public IllegalStateException getIllegalStateException(String msg, Throwable e) {
        logger.error(msg, e);
        return new IllegalStateException(msg, e);
    }

    public IllegalArgumentException getIllegalArgumentException(String msg) {
        return getIllegalArgumentException(msg, null);
    }

    public IllegalArgumentException getIllegalArgumentException(String msg, Throwable e) {
        logger.error(msg, e);
        return new IllegalArgumentException(msg, e);
    }

    public UnsupportedOperationException getUnsupportedOperationException(String msg) {
        logger.error(msg);
        return new UnsupportedOperationException(msg);
    }

  //  public ErrorInfo getErrorInfo(CharSequence requestUrl, Exception e, int errCode) {
   //     logger.error("Exception at request " + requestUrl);
   //     return null; //new ErrorInfo(requestUrl, e, errCode);
  //  }

      public ErrorInfo getErrorInfo(CharSequence requestUrl, Exception e, int errCode) {
         logger.error("Exception at request " + requestUrl);
         return new ErrorInfo(errCode, e.getMessage());
      }

    public NotFoundException getNotFoundException(String reason) {
        logger.error(reason);
        return new NotFoundException(reason);
    }

}