package com.uakruk.fileexchanger.util;

import com.uakruk.fileexchanger.dto.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * fileexchanger class
 *
 * @author Yaroslav Kruk on 9/25/17.
 * e-mail: yakruck@gmail.com
 * GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.8
 */
@Slf4j
@ControllerAdvice
public class SystemExceptionHandler {

    @ExceptionHandler(RequestedResourceNotFountException.class)
    public ResponseEntity<?> handleException(RequestedResourceNotFountException e) {
        ErrorMessage message = new ErrorMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @Order(Ordered.LOWEST_PRECEDENCE)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleSystemException(Exception e) {
        ErrorMessage message = new ErrorMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
