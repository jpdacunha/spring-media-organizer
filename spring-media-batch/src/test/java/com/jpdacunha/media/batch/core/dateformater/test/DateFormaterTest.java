package com.jpdacunha.media.batch.core.dateformater.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.jpdacunha.media.batch.core.utils.DateUtils;

public class DateFormaterTest {
	
	private static final String PATTERN1 = "yyyyMMdd";
	private static final String PATTERN2 = "yyyyMMdd_HHmmss";
	private static final String PATTERN3 = "yyyyMMddHHmmss";
	
	private static final SimpleDateFormat formater1 = new SimpleDateFormat(PATTERN1);
	private static final SimpleDateFormat formater2 = new SimpleDateFormat(PATTERN2);
	private static final SimpleDateFormat formater3 = new SimpleDateFormat(PATTERN3);

	@Test()
	public void pattern1() {		
		Date date = DateUtils.toDate(formater1, "20240925");
		Assert.assertTrue(date != null);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Assert.assertEquals(2024, calendar.get(Calendar.YEAR));
	}
	
	@Test()
	public void pattern1_error_month() {
		Date date = DateUtils.toDate(formater1, "20242425");
		Assert.assertTrue(date == null);
	}
	
	@Test()
	public void pattern1_error_day() {
		Date date = DateUtils.toDate(formater1, "20240464");
		Assert.assertTrue(date == null);	
	}
	
	@Test()
	public void pattern1_Bad_length() {
		Date date = DateUtils.toDate(formater1, "2024046412");
		Assert.assertTrue(date == null);	
	}
	
	@Test()
	public void pattern2() {	
		Date date = DateUtils.toDate(formater2, "20240911_121509");
		Assert.assertTrue(date != null);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Assert.assertEquals(2024, calendar.get(Calendar.YEAR));
		Assert.assertEquals(11, calendar.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(12, calendar.get(Calendar.HOUR_OF_DAY));
	}
	
	@Test()
	public void pattern2_error_month() {
		Date date = DateUtils.toDate(formater2, "20242411_121509");
		Assert.assertTrue(date == null);
	}
	
	@Test()
	public void pattern2_error_day() {
		Date date = DateUtils.toDate(formater2, "20240962_121509");
		Assert.assertTrue(date == null);	
	}
	
	@Test()
	public void pattern2_Bad_length() {
		Date date = DateUtils.toDate(formater2, "20240911__121509");
		Assert.assertTrue(date == null);	
	}
	
	@Test()
	public void pattern3() {	
		Date date = DateUtils.toDate(formater3, "20240911110612");
		Assert.assertTrue(date != null);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Assert.assertEquals(2024, calendar.get(Calendar.YEAR));
		Assert.assertEquals(11, calendar.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(11, calendar.get(Calendar.HOUR_OF_DAY));
	}
	
	@Test()
	public void pattern3_error_month() {
		Date date = DateUtils.toDate(formater3, "20243211110612");
		Assert.assertTrue(date == null);
	}
	
	@Test()
	public void pattern3_error_day() {
		Date date = DateUtils.toDate(formater3, "20240989110612");
		Assert.assertTrue(date == null);	
	}

}
