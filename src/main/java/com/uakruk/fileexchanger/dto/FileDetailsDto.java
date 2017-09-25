package com.uakruk.fileexchanger.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Calendar;

/**
 * fileexchanger class
 *
 * @author Yaroslav Kruk on 9/24/17.
 * e-mail: yakruck@gmail.com
 * GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.8
 */
@Data
@ToString
public class FileDetailsDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    @JsonIgnore
    private File file;

    @JsonIgnore
    private String originalName;

    public FileDetailsDto() {}

    public FileDetailsDto(String token, String description) {
        this.token = token;
        this.description = description;
    }

    public FileDetailsDto(File file, String originalName, String description) {
        this.file = file;
        this.originalName = originalName;
        this.description = description;
    }

}
