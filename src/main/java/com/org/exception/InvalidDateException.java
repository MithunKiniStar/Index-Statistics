package com.org.exception;

/**
 * @author Mithun Kini on 16/Feb/2020.
 *
 * InvalidDateException - This exception is thrown during parsing invalid date
 *
 */
public class InvalidDateException extends RuntimeException {

      int code;
    public InvalidDateException(String message, int code) {
        super(message);
        this.code = code;
    }


}
