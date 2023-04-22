package com.rohila.api.utils.controller;

import static com.rohila.api.utils.constant.AppConstant.*;
import static com.rohila.api.utils.constant.BatchConstant.*;

import java.util.Date;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to handle requests to start the batch processing
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
@RestController
public class BatchController {

    /** Logger declaration */
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchController.class);

    /** Autowired dependency for {@link JobLauncher} */
    @Autowired private JobLauncher jobLauncher;

    /** Autowired dependency for {@link Job} */
    @Autowired
    @Qualifier(MIGRATE_DATA_JOB)
    private Job job;

    @GetMapping(EXECUTE_URI)
    public String executeBatch(
            @RequestParam("source") String source, @RequestParam("destination") String destination)
            throws Exception {
        StopWatch watch = new StopWatch();
        watch.start();
        LOGGER.info("Started execution of batch job at time = [{}]", System.currentTimeMillis());
        JobParameters jobParameters =
                new JobParametersBuilder()
                        .addString(JOB_ID, UUID.randomUUID().toString())
                        .addDate(DATE, new Date())
                        .addLong(TIME, System.currentTimeMillis())
                        .addString(SOURCE, source)
                        .addString(DESTINATION, destination)
                        .toJobParameters();
        LOGGER.debug("executing batch job with jobParameters", jobParameters);
        jobLauncher.run(job, jobParameters);
        watch.stop();
        LOGGER.info("Finished execution of batch job at time = [{}]", System.currentTimeMillis());
        return "Job is finished in seconds = " + watch.getTotalTimeSeconds();
    }
}
