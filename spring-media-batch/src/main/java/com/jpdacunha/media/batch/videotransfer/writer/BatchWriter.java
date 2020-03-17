package com.jpdacunha.media.batch.videotransfer.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.jpdacunha.media.batch.videotransfer.dto.MovieDescriptor;

public class BatchWriter implements ItemWriter<MovieDescriptor> {
	
	private static Logger log = LoggerFactory.getLogger(BatchWriter.class);

	@Override
	public void write(List<? extends MovieDescriptor> items) throws Exception {
		log.info("Writing ["  + items.size() + "] elements ...");
		
	}

}
