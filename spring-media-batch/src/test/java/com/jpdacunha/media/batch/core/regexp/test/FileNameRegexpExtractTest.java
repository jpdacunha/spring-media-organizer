package com.jpdacunha.media.batch.core.regexp.test;

import org.junit.Assert;
import org.junit.Test;

import com.jpdacunha.media.batch.core.utils.RegexpUtils;

public class FileNameRegexpExtractTest {
		
	private static final String PATTERN1 = "(\\d{8})";
	private static final String PATTERN2 = "^(\\d{8})(_)(\\d{6})";
	private static final String PATTERN3 = "(\\d{14})";
	
	@Test()
	public void pattern1() {		
		String pattern = PATTERN1;
		String value = "IMG-20240925-WA0007.jpg";
		String extracted = RegexpUtils.extractFirst(pattern, value);
		Assert.assertEquals("20240925", extracted);
	}
	
	
	@Test()
	public void pattern1_error_begin() {
		String pattern = PATTERN1;
		String value = "IMG-A0240925-WA0007.jpg";
		String extracted = RegexpUtils.extractFirst(pattern, value);
		Assert.assertEquals(null, extracted);	
	}
	
	@Test()
	public void pattern1_error_end() {
		String pattern = PATTERN1;
		String value = "IMG-2024092A-WA0007.jpg";
		String extracted = RegexpUtils.extractFirst(pattern, value);
		Assert.assertEquals(null, extracted);	
	}
	
	@Test()
	public void pattern2() {	
		String pattern = PATTERN2;
		String value = "20240911_121509.jpg";
		String extracted = RegexpUtils.extractFirst(pattern, value);
		Assert.assertEquals("20240911_121509", extracted);
	}
	
	
	@Test()
	public void pattern2_error_separator() {
		String pattern = PATTERN2;
		String value = "20240911-121509.jpg";
		String extracted = RegexpUtils.extractFirst(pattern, value);
		Assert.assertEquals(null, extracted);
	}
	
	@Test()
	public void pattern2_error_end() {
		String pattern = PATTERN2;
		String value = "20240911_12150A.jpg";
		String extracted = RegexpUtils.extractFirst(pattern, value);
		Assert.assertEquals(null, extracted);
	}
	
	@Test()
	public void pattern3() {
		String pattern = PATTERN3;
		String value = "IMG20240911110612.jpg";
		String extracted = RegexpUtils.extractFirst(pattern, value);
		Assert.assertEquals("20240911110612", extracted);
	}
	
	@Test()
	public void pattern3_error_length() {
		String pattern = PATTERN3;
		String value = "IMG202409111106A12.jpg";
		String extracted = RegexpUtils.extractFirst(pattern, value);
		Assert.assertEquals(null, extracted);
	}
	
	@Test()
	public void pattern3_error_end() {
		String pattern = PATTERN3;
		String value = "IMG2024091111061A.jpg";
		String extracted = RegexpUtils.extractFirst(pattern, value);
		Assert.assertEquals(null, extracted);
	}
	
	@Test()
	public void pattern3_error_begin() {
		String pattern = PATTERN3;
		String value = "IMGA0240911110612.jpg";
		String extracted = RegexpUtils.extractFirst(pattern, value);
		Assert.assertEquals(null, extracted);
	}


}
