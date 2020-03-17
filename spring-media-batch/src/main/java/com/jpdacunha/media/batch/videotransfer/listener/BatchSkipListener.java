package com.jpdacunha.media.batch.videotransfer.listener;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

import com.jpdacunha.media.batch.videotransfer.dto.MovieDescriptor;

public class BatchSkipListener implements SkipListener<File, MovieDescriptor> {
	
	private static Logger log = LoggerFactory.getLogger(BatchSkipListener.class);

	@Override
	public void onSkipInRead(Throwable t) {
		log.info("onWriteError : throwable:" + t.getClass().getName());
		
	}

	@Override
	public void onSkipInWrite(MovieDescriptor item, Throwable t) {
		log.info("onWriteError item:" + item.getClass().getName() + " throwable:" + t.getClass().getName());
		
	}

	@Override
	public void onSkipInProcess(File item, Throwable t) {
		log.info("onWriteError item:" + item.getClass().getName() + " throwable:" + t.getClass().getName());
	}

}
