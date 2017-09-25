package com.uakruk.fileexchanger.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * fileexchanger class
 *
 * @author Yaroslav Kruk on 9/25/17.
 * e-mail: yakruck@gmail.com
 * GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.8
 */
@Component
@Slf4j
public class FileUtils {

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${files.path}")
    String basePath;

    public File getByLocation(String location) {
        log.info("Requested file by location = {}", location);

        File file = getFileByLocation(location);

        return file;
    }

    public void deleteFiles() {

    }

    public void deleteFile(String location) {
        log.info("Deleting file with location = {}", location);

        File file = getFileByLocation(location);

        if (!file.delete()) {
            throw new RuntimeException("Failed to delete the file");
        }
    }

    private File getFileByLocation(String location) {
        Resource resource = resourceLoader.getResource(basePath + location);

        File file = null;
        try {
            file = resource.getFile();
        } catch (FileNotFoundException e) {
            file = new File(basePath + location);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Failed to fetch file {}", location);
        }

        return file;
    }

    public void makePath(String location) {
        File file = getFileByLocation(location);

        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public void saveFile(MultipartFile file, String location) throws IOException {

        File savingResult = getFileByLocation(location);

        if (!savingResult.exists()) {
            try {
                savingResult.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        file.transferTo(savingResult);

    }

    public UUID generateFileLocationAndToken() {
        UUID uuid = UUID.randomUUID();
        log.info("Generated uuid = {}", uuid.toString());

        return uuid;
    }

    public String getFilePath(UUID uuid) {
        String path = uuid.toString();

        path = path.substring(0, path.lastIndexOf("-")).replaceAll("-", "/") + "/";

        return path;
    }

    public String getFileName(UUID uuid) {
        String fileName = uuid.toString();

        return fileName.substring(fileName.lastIndexOf("-") + 1);
    }
}
