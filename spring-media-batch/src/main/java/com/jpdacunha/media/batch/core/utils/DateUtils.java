package com.jpdacunha.media.batch.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	
	private static Logger log = LoggerFactory.getLogger(DateUtils.class);
	
	public static Date toDate(SimpleDateFormat formater, String dateStr) {
		
		Date date = null;
		
		if (formater != null) {
			
			if (!StringUtils.isEmpty(dateStr)) {
				
				try {
					
					//Enable SimpleDateFormat Strict mode
					formater.setLenient(false);
					date = formater.parse(dateStr);
				} catch (ParseException e) {
					log.warn("Unparsable String [" + dateStr + "] against [" + formater.toPattern() + "]");
				}
				
			} else {
				log.warn("Unable to convert null value");
			}
			
		} else {
			log.warn("Invalid null formater");
		}
		
		return date;
		
	}

}
