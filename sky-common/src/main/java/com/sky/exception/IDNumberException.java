package com.sky.exception;

/**
 * 账号被锁定异常
 */
public class IDNumberException extends BaseException {

    public IDNumberException() {
    }

    public IDNumberException(String msg) {
        super(msg);
    }

}
