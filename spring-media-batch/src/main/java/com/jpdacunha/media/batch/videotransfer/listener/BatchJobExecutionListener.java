package com.jpdacunha.media.batch.videotransfer.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class BatchJobExecutionListener implements JobExecutionListener {
	
	private static Logger log = LoggerFactory.getLogger(BatchJobExecutionListener.class);

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("Begining of job  " + jobExecution.getJobInstance().getJobName() + " at " + jobExecution.getStartTime());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		String exitCode = jobExecution.getExitStatus().getExitCode();
		log.info("End of job  " + jobExecution.getJobInstance().getJobName() + " at " + jobExecution.getEndTime() + " with status " + exitCode);
	}

}
