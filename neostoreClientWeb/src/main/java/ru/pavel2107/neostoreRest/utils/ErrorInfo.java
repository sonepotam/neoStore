package ru.pavel2107.neostoreRest.utils;

/**
 * Created by pavel2107 on 16.12.15.
 * Ошибка, которая передается на клиента в JSON
 */
public class ErrorInfo extends RuntimeException {

    private int errCode;
    private String errMessage;

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

}