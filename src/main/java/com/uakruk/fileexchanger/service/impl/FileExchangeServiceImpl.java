package com.uakruk.fileexchanger.service.impl;

import com.uakruk.fileexchanger.dao.FileDetailsDao;
import com.uakruk.fileexchanger.dto.FileDetailsDto;
import com.uakruk.fileexchanger.entity.FileDetails;
import com.uakruk.fileexchanger.service.FileExchangeService;
import com.uakruk.fileexchanger.util.FileUtils;
import com.uakruk.fileexchanger.util.RequestedResourceNotFountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * fileexchanger class
 *
 * @author Yaroslav Kruk on 9/24/17.
 * e-mail: yakruck@gmail.com
 * GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.8
 */
@Slf4j
@Service
@Transactional
public class FileExchangeServiceImpl implements FileExchangeService {

    private final FileDetailsDao fileDetailsDao;

    private final FileUtils fileUtils;

    private int expiringDays;


    @Autowired
    public FileExchangeServiceImpl(FileDetailsDao fileDetailsDao, FileUtils fileUtils, Environment env) {
        this.fileDetailsDao = fileDetailsDao;
        this.fileUtils = fileUtils;
        expiringDays = Integer.parseInt(env.getProperty("file.old.days"));

    }

    @Override
    public FileDetailsDto get(String token) {
        FileDetails fileDetails = fileDetailsDao.getByToken(token);

        if (fileDetails == null) {
            throw new RequestedResourceNotFountException(token);
        }

        File file = fileUtils.getByLocation(getLocation(fileDetails.getFileToken()));

        return new FileDetailsDto(file, fileDetails.getOriginalName(), fileDetails.getDescription());
    }

    @Override
    public FileDetailsDto addDescription(String token, String description) {
        FileDetails fileDetails = fileDetailsDao.getByToken(token);

        if (fileDetails == null) {
            throw new RequestedResourceNotFountException(token);
        }

        fileDetails.setDescription(description);

        fileDetailsDao.update(fileDetails);
        return new FileDetailsDto(token, description);
    }

    @Override
    public void delete(String token) {
        FileDetails fileDetails = fileDetailsDao.getByToken(token);

        if (fileDetails == null) {
            throw new RequestedResourceNotFountException(token);
        }

        fileUtils.deleteFile(getLocation(fileDetails.getFileToken()));
        fileDetailsDao.delete(fileDetails);
    }

    @Override
    public FileDetailsDto save(MultipartFile file, String description) {

        FileDetails fileDetails = new FileDetails();

        UUID fileLocationAndToken = fileUtils.generateFileLocationAndToken();
        String fileToken = fileLocationAndToken.toString();
        String filePath = fileUtils.getFilePath(fileLocationAndToken);
        String fileName = fileUtils.getFileName(fileLocationAndToken);
        String location = filePath + fileName;

        fileDetails.setFileToken(fileToken);
        fileDetails.setOriginalName(file.getOriginalFilename());

        Calendar expired = Calendar.getInstance();
        expired.add(Calendar.DAY_OF_MONTH, expiringDays);

        fileDetails.setExpired(expired);
        fileDetails.setDescription(description);

        try {
            fileUtils.makePath(filePath);
            fileUtils.saveFile(file, location);
            fileDetailsDao.create(fileDetails);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new FileDetailsDto(fileToken, description);
    }

    @Override
    public void deleteOldFiles() {
        log.info("Getting the entire list of files");
        List<FileDetails> fileDetailsList = fileDetailsDao.getAll();
        log.info("Fetched files : {}", fileDetailsList.size());

        fileDetailsList.stream()
                .filter(f -> old(f.getExpired()))
                .forEach(f -> {
                    log.info("Filtered files: {}", f);
                    fileUtils.deleteFile(getLocation(f.getFileToken()));
                    fileDetailsDao.delete(f);
                });
    }

    private boolean old(Calendar expired) {
        Calendar now = Calendar.getInstance();

        return now.after(expired);
    }

    private String getLocation(String token) {
        return token.replaceAll("-", "/");
    }
}
