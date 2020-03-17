package com.jpdacunha.media.batch.videotransfer.processor;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.jpdacunha.media.batch.videotransfer.dto.MovieDescriptor;

public class BatchProcessor implements ItemProcessor<File, MovieDescriptor> {
	
	private static Logger log = LoggerFactory.getLogger(BatchProcessor.class);

	@Override
	public MovieDescriptor process(File item) throws Exception {
		log.info("Processing ["  + item.getName() + "] ...");
		return null;
	}

}
