package com.androidcourse.client.weather.processor;

/*
 * This class helps to provide a locking mechanism for the WeatherDTO to be
 * updated/read in thread safe manner. The reader thread is WeatherDriver or
 * Activity thread and the writer thread is the WeatherDataGenerator.
 */
public enum WeatherDays {
	TODAY(0)/*-,
			TODAY_PLUS_1(1),
			TODAY_PLUS_2(2),
			TODAY_PLUS_3(3)*/;
	
	final int idx;
	
	WeatherDays(int idx) {
		this.idx = idx;
	}
	
	int getRelativeDay() {
		return idx;
	}
}
