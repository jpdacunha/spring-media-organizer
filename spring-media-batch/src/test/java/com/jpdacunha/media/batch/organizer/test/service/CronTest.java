package com.jpdacunha.media.batch.organizer.test.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class CronTest {
	
	private static Logger log = LoggerFactory.getLogger(CronTest.class);
	
	@Test
	public void testScheduler(){
		
	    org.springframework.scheduling.support.CronTrigger trigger = new CronTrigger("0 0 1 * * *");
	    Calendar today = Calendar.getInstance();

	    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss EEEE"); 
	    final Date currentDate = today.getTime();
	    log.info("currentDate : " + df.format(currentDate));
	    Date nextExecutionTime = trigger.nextExecutionTime(
	        new TriggerContext() {

	            @Override
	            public Date lastScheduledExecutionTime() {
	                return currentDate;
	            }

	            @Override
	            public Date lastActualExecutionTime() {
	                return currentDate;
	            }

	            @Override
	            public Date lastCompletionTime() {
	                return currentDate;
	            }
	        });

	    log.info("nextExecutionTime : " + df.format(nextExecutionTime));

	}

}
