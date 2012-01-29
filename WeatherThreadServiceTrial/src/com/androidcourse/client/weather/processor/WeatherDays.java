package com.androidcourse.client.weather.processor;

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
}
