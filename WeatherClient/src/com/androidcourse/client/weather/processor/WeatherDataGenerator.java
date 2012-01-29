package com.androidcourse.client.weather.processor;

import com.androidcourse.client.weather.data.WeatherDTO;

class WeatherDataGenerator implements Runnable {
	final WeatherDTO weatherDTO;
	final WeatherDays day;
	
	WeatherDataGenerator(WeatherDays day) {
		weatherDTO = new WeatherDTO();
		this.day = day;
	}
	
	@Override
	public void run() {
		synchronized (day) {
			weatherDTO
					.setCelsiusTemp((float) System.currentTimeMillis());
			weatherDTO
					.setFarenheitTemp((float) System.currentTimeMillis());
			weatherDTO
					.setConditions(String.valueOf(System.currentTimeMillis()
							+ day.getRelativeDay()));
		}
	}
	
	public WeatherDTO getWeather() {
		return weatherDTO;
	}
	
}
