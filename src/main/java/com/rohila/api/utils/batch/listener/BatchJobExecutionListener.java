package com.rohila.api.utils.batch.listener;

import static com.rohila.api.utils.constant.BatchConstant.BATCH_JOB_EXECUTION_LISTENER;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * Class file create a listener for mongo job
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
@Component(BATCH_JOB_EXECUTION_LISTENER)
public class BatchJobExecutionListener implements JobExecutionListener {

    /** Logger declaration */
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchJobExecutionListener.class);

    /**
     * Callback before a job executes.
     *
     * @param jobExecution the current {@link JobExecution}
     */
    @Override
    public void beforeJob(JobExecution jobExecution) {
        LOGGER.info("Before Job :: Running Job Execution with parameters = [{}]", jobExecution);
    }

    /**
     * Callback after completion of a job. Called after both successful and failed executions. To
     * perform logic on a particular status, use "if (jobExecution.getStatus() == BatchStatus.X)".
     *
     * @param jobExecution the current {@link JobExecution}
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        LOGGER.info("After Job :: Running Job Execution with parameters = [{}]", jobExecution);
    }
}
