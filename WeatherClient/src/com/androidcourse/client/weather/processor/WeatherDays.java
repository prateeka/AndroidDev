package com.androidcourse.client.weather.processor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
 * This class represents the days for whom the weather data is to be downloaded
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
