package com.uakruk.fileexchanger.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.uakruk.fileexchanger.dto.FileDetailsDto;
import com.uakruk.fileexchanger.entity.FileDetails;
import com.uakruk.fileexchanger.service.FileExchangeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * fileexchanger class
 *
 * @author Yaroslav Kruk on 9/24/17.
 * e-mail: yakruck@gmail.com
 * GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.8
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileExchangerController {

    @Autowired
    private FileExchangeService fileService;

    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file, @RequestParam(required = false) String description) {
        log.info("Uploaded new file = {}, size = {} ", file.getOriginalFilename(), file.getSize());

        FileDetailsDto response = fileService.save(file, description);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping(value = "/{fileToken}")
    public void getFile(@PathVariable String fileToken, HttpServletResponse response) throws IOException {
        log.info("Requested file with token = {}", fileToken);

        FileDetailsDto fileDetailsDto= fileService.get(fileToken);
        File file = fileDetailsDto.getFile();
        InputStream in = null;
        OutputStream out = null;

        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileDetailsDto.getOriginalName() + "\"");
        try {
            in = new FileInputStream(file);
            out = response.getOutputStream();
            IOUtils.copy(in, out);
        } finally {
            in.close();
            out.close();
        }

    }

    @GetMapping(value = "/{fileToken}/description")
    public ResponseEntity<?> getFileDescription(@PathVariable String fileToken) {
        log.info("Requested file description = {}", fileToken);

        FileDetailsDto fileDetailsDto = fileService.get(fileToken);

        return ResponseEntity.ok(fileDetailsDto);
    }

    @DeleteMapping(value = "/{fileToken}")
    public ResponseEntity<?> deleteFile(@PathVariable String fileToken) {
        log.info("Deleting file with token = {}", fileToken);

        fileService.delete(fileToken);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/{fileToken}")
    public ResponseEntity<?> addDescription(@PathVariable String fileToken, @RequestBody FileDetailsDto descriptionDto) {
        log.info("Adding description = {} to the file with token = {}", descriptionDto, fileToken);

        FileDetailsDto fileDetailsDto = fileService.addDescription(fileToken, descriptionDto.getDescription());

        return ResponseEntity.ok(fileDetailsDto);
    }
}
