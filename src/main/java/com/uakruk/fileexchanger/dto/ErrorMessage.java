package com.uakruk.fileexchanger.dto;

import lombok.Data;

/**
 * fileexchanger class
 *
 * @author Yaroslav Kruk on 9/25/17.
 * e-mail: yakruck@gmail.com
 * GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.8
 */
@Data
public class ErrorMessage {

    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }
}
