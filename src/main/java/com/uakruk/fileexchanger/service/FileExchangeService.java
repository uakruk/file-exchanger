package com.uakruk.fileexchanger.service;

import com.uakruk.fileexchanger.dto.FileDetailsDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * fileexchanger class
 *
 * @author Yaroslav Kruk on 9/24/17.
 * e-mail: yakruck@gmail.com
 * GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.8
 */
public interface FileExchangeService {

    FileDetailsDto get(String token);

    FileDetailsDto addDescription(String token, String description);

    void delete(String token);

    FileDetailsDto save(MultipartFile file, String description);

    void deleteOldFiles();
}
