package com.jpdacunha.media.batch.videotransfer.listener;

import java.util.List;

import javax.batch.api.chunk.listener.ItemWriteListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchItemWriteListener implements ItemWriteListener {

	private static Logger log = LoggerFactory.getLogger(BatchItemWriteListener.class);
	
	@Override
	public void beforeWrite(List<Object> items) throws Exception {
		log.info("beforeWrite items:" + items.size());
	}

	@Override
	public void afterWrite(List<Object> items) throws Exception {
		log.info("afterWrite items:" + items.size());

	}

	@Override
	public void onWriteError(List<Object> items, Exception ex) throws Exception {
		log.info("onWriteError items:" + items.size() + " exception:" + ex.getClass().getName());
	}

}
