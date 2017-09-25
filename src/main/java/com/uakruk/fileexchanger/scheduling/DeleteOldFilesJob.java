package com.uakruk.fileexchanger.scheduling;

import com.uakruk.fileexchanger.service.FileExchangeService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component
public class DeleteOldFilesJob implements Job {

    @Autowired
    FileExchangeService fileExchangeService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Started removing old files");

        fileExchangeService.deleteOldFiles();
    }

}
