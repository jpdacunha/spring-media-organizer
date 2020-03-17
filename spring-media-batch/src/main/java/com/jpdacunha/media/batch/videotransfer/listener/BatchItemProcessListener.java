package com.jpdacunha.media.batch.videotransfer.listener;

import javax.batch.api.chunk.listener.ItemProcessListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchItemProcessListener implements ItemProcessListener {
	
	private static Logger log = LoggerFactory.getLogger(BatchItemProcessListener.class);

	@Override
	public void beforeProcess(Object item) throws Exception {
		log.info("beforeProcess " + item.getClass().getName());
	}

	@Override
	public void afterProcess(Object item, Object result) throws Exception {
		log.info("afterProcess item:" + item.getClass().getName() + " result:"+result.getClass().getName());

	}

	@Override
	public void onProcessError(Object item, Exception ex) throws Exception {
		log.info("afterProcess item:" + item.getClass().getName() + " exception:"+ex.getClass().getName());

	}

}
