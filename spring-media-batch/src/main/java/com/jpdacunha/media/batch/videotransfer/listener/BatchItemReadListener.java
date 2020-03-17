package com.jpdacunha.media.batch.videotransfer.listener;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

public class BatchItemReadListener implements ItemReadListener<File> {
	
	private static Logger log = LoggerFactory.getLogger(BatchItemReadListener.class);

	@Override
	public void beforeRead() {
		log.info("beforeRead");
	}

	@Override
	public void afterRead(File item) {
		log.info("afterRead item:" + item.getClass().getName());
		
	}

	@Override
	public void onReadError(Exception ex) {
		log.info("onReadError result:" + ex.getClass().getName());
		
	}

}
