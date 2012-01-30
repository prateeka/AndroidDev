package com.androidcourse.client.weather.processor;

import com.androidcourse.client.weather.data.WeatherDTO;
import com.arya.androidcourse.service.http.IHttpService;

class WeatherDataGenerator implements Runnable {
	final WeatherDTO weatherDTO;
	final WeatherDays day;
	final IHttpService httpService;
	
	WeatherDataGenerator(IHttpService httpService, WeatherDays day) {
		weatherDTO = new WeatherDTO();
		this.day = day;
		this.httpService = httpService;
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
