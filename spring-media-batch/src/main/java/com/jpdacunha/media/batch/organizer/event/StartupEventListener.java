package com.jpdacunha.media.batch.organizer.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.jpdacunha.media.batch.organizer.service.ConfigurationService;
import com.jpdacunha.media.batch.organizer.service.impl.MediaServiceImpl;

@Component
public class StartupEventListener implements ApplicationListener<ContextRefreshedEvent> {
	
	private static Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);
	
	@Autowired
	private ConfigurationService configurationService;
	
    @Override 
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	
    	log.info("## Executing starting event");
 
    	configurationService.verifyConfiguration();
    	
    	log.info("## Done.");

    }

}
