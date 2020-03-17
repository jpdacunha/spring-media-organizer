package com.jpdacunha.media.batch.videotransfer.configuration;

import java.io.File;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.jpdacunha.media.batch.videotransfer.dto.MovieDescriptor;
import com.jpdacunha.media.batch.videotransfer.listener.BatchJobExecutionListener;
import com.jpdacunha.media.batch.videotransfer.processor.BatchProcessor;
import com.jpdacunha.media.batch.videotransfer.reader.BatchReader;
import com.jpdacunha.media.batch.videotransfer.utils.BatchStepSkipperPolicy;
import com.jpdacunha.media.batch.videotransfer.writer.BatchWriter;

@Configuration
@EnableBatchProcessing
public class VideoTransfertBatchConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public JobRepository jobRepositoryObj() throws Exception {
		JobRepositoryFactoryBean jobRepoFactory = new JobRepositoryFactoryBean();
		jobRepoFactory.setTransactionManager(transactionManager);
		jobRepoFactory.setDataSource(dataSource);
		return jobRepoFactory.getObject();
	}
	
	@Bean
	public BatchReader batchReader() {
		return new BatchReader();
	}
	
	@Bean
	public BatchProcessor batchProcessor() {
		return new BatchProcessor();
	}
	
	@Bean
	public BatchWriter batchWriter() {
		return new BatchWriter();
	}
	
	@Bean
	public BatchJobExecutionListener batchJobListener() {
		return new BatchJobExecutionListener();
	}
	
	@Bean
	public BatchStepSkipperPolicy batchStepSkipper() {
		return new BatchStepSkipperPolicy();
	}
	

	@Bean
	public Step batchStep() {
		return stepBuilderFactory.get("stepVideoTransfert")
				//.transactionManager(transactionManager)
				.<File, MovieDescriptor>chunk(1)
				.reader(batchReader())
				.processor(batchProcessor())
				.writer(batchWriter())
				.faultTolerant()
				.skipPolicy(batchStepSkipper())
				.build();
	}

	@Bean
	public Job jobStep() throws Exception {
		return jobBuilderFactory.get("jobVideoTransfert")
				//.repository(jobRepositoryObj())
				.incrementer(new RunIdIncrementer())
				.listener(batchJobListener())
				.flow(batchStep()).end().build();
	}

}
