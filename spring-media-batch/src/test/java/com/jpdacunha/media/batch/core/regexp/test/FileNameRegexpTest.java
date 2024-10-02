package com.jpdacunha.media.batch.core.regexp.test;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpdacunha.media.batch.core.utils.RegexpUtils;

public class FileNameRegexpTest {
	
	private static Logger log = LoggerFactory.getLogger(FileNameRegexpTest.class);
	private static final String PATTERN1 = "^(IMG-)(\\d{8})(-WA)(\\d{4})(.jpg)$";
	private static final String PATTERN2 = "^(\\d{8})(_)(\\d{6})(.jpg)$";
	private static final String PATTERN3 = "^(IMG)(\\d{14})(.jpg)$";
	
	@Test()
	public void pattern1() {
		
		String pattern = PATTERN1;
		String value = "IMG-20151225-WA0005.jpg";
		Assert.assertTrue(RegexpUtils.match(pattern, value));
		
	}
	
	@Test()
	public void pattern11() {
		
		String pattern = PATTERN1;
		String value = "IMG-20190425-WA0007.jpg";
		Assert.assertTrue(RegexpUtils.match(pattern, value));
		
	}
	
	@Test()
	public void pattern1_error_begin() {
		
		String pattern = PATTERN1;
		String value = "AAA-20240925-WA0007.jpg";
		Assert.assertFalse(RegexpUtils.match(pattern, value));
		
	}
	
	@Test()
	public void pattern1_error_end() {
		
		String pattern = PATTERN1;
		String value = "IMG-20240925-WA0007.gif";
		Assert.assertFalse(RegexpUtils.match(pattern, value));
		
	}
	
	@Test()
	public void pattern1_error_length() {
		
		String pattern = PATTERN1;
		String value = "IMG-123456789-WA0007.jpg";
		
		Assert.assertFalse(RegexpUtils.match(pattern, value));
	}
	
	@Test()
	public void pattern2() {
		
		String pattern = PATTERN2;
		String value = "20240911_121509.jpg";
		Assert.assertTrue(RegexpUtils.match(pattern, value));
		
	}
	
	@Test()
	public void pattern2_error_separator() {
		
		String pattern = PATTERN2;
		String value = "20240911-121509.jpg";
		Assert.assertFalse(RegexpUtils.match(pattern, value));
		
	}
	
	@Test()
	public void pattern2_error_end() {
		
		String pattern = PATTERN2;
		String value = "20240911_121509.png";
		Assert.assertFalse(RegexpUtils.match(pattern, value));
		
	}
	
	@Test()
	public void pattern2_error_length() {
		
		String pattern = PATTERN2;
		String value = "20240911_1234567.jpg";
		
		Assert.assertFalse(RegexpUtils.match(pattern, value));
	}
	
	@Test()
	public void pattern2_error_letters() {
		
		String pattern = PATTERN2;
		String value = "2024091A_1234567.jpg";
		
		Assert.assertFalse(RegexpUtils.match(pattern, value));
	}
	
	@Test()
	public void pattern3() {
		
		String pattern = PATTERN3;
		String value = "IMG20240911110612.jpg";
		Assert.assertTrue(RegexpUtils.match(pattern, value));
		
	}
	
	@Test()
	public void pattern3_error_length() {
		
		String pattern = PATTERN3;
		String value = "IMG202409111106120.jpg";
		Assert.assertFalse(RegexpUtils.match(pattern, value));
		
	}
	
	@Test()
	public void pattern3_error_end() {
		
		String pattern = PATTERN3;
		String value = "IMG202409111106120.tif";
		Assert.assertFalse(RegexpUtils.match(pattern, value));
		
	}
	
	@Test()
	public void pattern3_error_begin() {
		
		String pattern = PATTERN3;
		String value = "IAAA202409111106120.jpg";
		Assert.assertFalse(RegexpUtils.match(pattern, value));
		
	}


}
