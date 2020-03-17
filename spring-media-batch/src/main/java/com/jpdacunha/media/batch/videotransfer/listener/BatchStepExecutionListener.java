package com.jpdacunha.media.batch.videotransfer.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class BatchStepExecutionListener implements StepExecutionListener {
	
	private static Logger log = LoggerFactory.getLogger(BatchStepExecutionListener.class);

	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.info("Begining of step  " + stepExecution.getStepName());

	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("afterStep  " + stepExecution.getStepName());
		return null;
	}

}
