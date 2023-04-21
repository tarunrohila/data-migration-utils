package com.rohila.api.utils.batch.listener;

import static com.rohila.api.utils.constant.BatchConstant.BATCH_STEP_EXECUTION_LISTENER;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

/**
 * Class file create a listener for mongo step
 *
 * @author Tarun Rohila
 * @version 1.0
 * @since April 15, 2023
 */
@Component(BATCH_STEP_EXECUTION_LISTENER)
public class BatchStepExecutionListener implements StepExecutionListener {

    /** Logger declaration */
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchStepExecutionListener.class);

    /**
     * Initialize the state of the listener with the {@link StepExecution} from the current scope.
     *
     * @param stepExecution instance of {@link StepExecution}.
     */
    @Override
    public void beforeStep(StepExecution stepExecution) {
        LOGGER.info("Before Step :: Running Step Execution with parameters = [{}]", stepExecution);
    }

    /**
     * Give a listener a chance to modify the exit status from a step. The value returned will be
     * combined with the normal exit status using {@link ExitStatus#and(ExitStatus)}.
     *
     * <p>Called after execution of step's processing logic (both successful or failed). Throwing
     * exception in this method has no effect, it will only be logged.
     *
     * @param stepExecution {@link StepExecution} instance.
     * @return an {@link ExitStatus} to combine with the normal value. Return {@code null} to leave
     *     the old value unchanged.
     */
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.info("After Step ::Running Step Execution with parameters = [{}]", stepExecution);
        return stepExecution.getExitStatus();
    }
}
