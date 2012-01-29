package com.androidcourse.client.weather.processor;

import com.androidcourse.client.weather.data.WeatherDTO;

class WeatherDataGenerator implements Runnable {
	final WeatherDTO weatherDTO;
	
	WeatherDataGenerator() {
		weatherDTO = new WeatherDTO();
	}
	
	@Override
	public void run() {
	}
	
	public WeatherDTO getWeather() {
		return weatherDTO;
	}
	
}
