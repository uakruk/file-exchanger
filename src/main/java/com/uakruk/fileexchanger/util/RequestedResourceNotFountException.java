package com.uakruk.fileexchanger.util;

/**
 * fileexchanger class
 *
 * @author Yaroslav Kruk on 9/25/17.
 * e-mail: yakruck@gmail.com
 * GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.8
 */
public class RequestedResourceNotFountException extends RuntimeException {

    public RequestedResourceNotFountException() {
        super();
    }

    public RequestedResourceNotFountException(String s) {
        super("Failed to access resource with id = " + s);
    }

    public RequestedResourceNotFountException(String s, Throwable throwable) {
        super(s, throwable);
    }


}
