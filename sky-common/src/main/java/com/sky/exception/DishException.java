package com.sky.exception;

/**
 * 账号被锁定异常
 */
public class DishException extends BaseException {

    public DishException() {
    }

    public DishException(String msg) {
        super(msg);
    }

}
