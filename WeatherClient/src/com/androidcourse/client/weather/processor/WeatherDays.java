package com.androidcourse.client.weather.processor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
 * This class helps to provide a locking mechanism for the WeatherDTO to be
 * updated/read in thread safe manner. The reader thread is WeatherDriver or
 * Activity thread and the writer thread is the WeatherDataProvider.
 */
public enum WeatherDays {
	TODAY(0),
	TODAY_PLUS_1(1),
	TODAY_PLUS_2(2),
	TODAY_PLUS_3(3);
	
	final int idx;
	
	WeatherDays(int idx) {
		this.idx = idx;
	}
	
	int getRelativeDay() {
		return idx;
	}
	
	public String getDate() {
		String DATE_FORMAT = "EEE, MMM d, yyyy";
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, idx);
		String formattedDate = dateFormat.format(cal.getTime());
		return formattedDate;
	}
}
