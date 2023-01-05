package com.jpdacunha.media.batch.removeduplicatesfotos.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jpdacunha.media.batch.organizer.service.impl.MediaServiceImpl;
import com.jpdacunha.media.batch.removeduplicatesfotos.exception.RemoveDuplicateImageshException;
import com.jpdacunha.media.batch.removeduplicatesfotos.service.RemoveDuplicateImagesService;

@Service
public class RemoveDuplicateFotosServiceImpl implements RemoveDuplicateImagesService {
	
	private static Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);

	//0 0 2 * * *
	@Scheduled(cron = "0 */1 * * * *")
	public void removeDuplicateFotos() throws RemoveDuplicateImageshException {
		
		log.info("#######################################################################################");
		log.info("# Starting remove duplicates FOTOS //////> ...");
		log.info("#######################################################################################");
		
		
		log.info("#######################################################################################");
		log.info("# End FOTOS.");
		log.info("#######################################################################################");
		
	}

}
