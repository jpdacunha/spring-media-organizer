package com.jpdacunha.media.batch.organizer.model;

import java.io.File;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import lombok.ToString;

@ToString(callSuper=false, includeFieldNames=true)
public class DateDescriptor {
	
	private Date date;
	private long timestamp;
	private Locale locale;
	private int month;
	private int year;
	private String monthName;
	
	public DateDescriptor(long timestamp, Locale locale) {
		
		super();
		
		this.timestamp = timestamp;
		this.locale = locale;
		
		Date date = new Date(timestamp);
		this.date = date;
		Calendar calendar = Calendar.getInstance(locale);
		calendar.setTime(date);
		
		int year = calendar.get(Calendar.YEAR);
		this.year = year;
		
		int month = calendar.get(Calendar.MONTH) + 1;
		this.month = month;
		
		String monthName = Month.of(month).getDisplayName(TextStyle.FULL, locale);
		this.monthName = monthName;
		
	}
	
	public String getYearMonthAsStringPath(File destDir) {
		
		if (destDir != null) {
		
			String yearMonthDirName = this.getYear() + File.separator + StringUtils.capitalize(this.getMonthName());
			
			String yearMonthPath = destDir.getAbsolutePath() + File.separator + yearMonthDirName;
			
			return yearMonthPath;
			
		} else {
			return null;
		}
		
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	
	

}
