package com.jpdacunha.media.batch.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexpUtils {
	
	private static Logger log = LoggerFactory.getLogger(RegexpUtils.class);

	private RegexpUtils() {
		super();
	}
	
	public static boolean match(String patternStr, String value) {
		
		boolean returnedValue = false;
		
		if (!StringUtils.isEmpty(patternStr) && !StringUtils.isEmpty(value)) {
			
			Pattern pattern = Pattern.compile(patternStr);
			Matcher matcher = pattern.matcher(value); 
			returnedValue =  matcher.matches();
			
		}
		return returnedValue;

	}
	
	public static String extractFirst(String patternStr, String value) {
		
		String returnedValue = null;
		
		if (!StringUtils.isEmpty(patternStr) && !StringUtils.isEmpty(value)) {
			
			Pattern pattern = Pattern.compile(patternStr);
			Matcher matcher = pattern.matcher(value); 
			
			while (matcher.find()) {
			    String group = matcher.group();
			    int start = matcher.start();
			    int end = matcher.end();
			    log.info(group + " " + start + " " + end);
			    returnedValue = group;
			}
		}
		return returnedValue;
	}

}
