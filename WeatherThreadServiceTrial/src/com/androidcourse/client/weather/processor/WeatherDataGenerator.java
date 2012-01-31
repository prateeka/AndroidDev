package com.androidcourse.client.weather.processor;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.androidcourse.client.weather.data.WeatherDTO;
import com.androidcourse.client.weather.http.HttpProcessor;

class WeatherDataGenerator implements Runnable {
	private static Logger logger = LoggerFactory
			.getLogger(WeatherDataGenerator.class);
	
	final String TAG = "WeatherDataGenerator ";
	final WeatherDTO weatherDTO;
	final WeatherDays day;
	final HttpProcessor httpProcessor;
	final String URL = "http://google.com";
	
	WeatherDataGenerator(HttpProcessor httpProcessor, WeatherDays day) {
		weatherDTO = new WeatherDTO();
		this.day = day;
		this.httpProcessor = httpProcessor;
		if (httpProcessor == null) {
			logger.error("httpProcessor is null..cannot execute normally ");
		}
	}
	
	@Override
	public void run() {
		try {
			String response = downloadWeatherUsingHTTP();
			processResponse(response);
		}
		catch (RemoteException e) {
			logger.error("RemoteException encountered : " + e);
		}
	}
	
	private void processResponse(String response) {
		logger.debug("outside synch processResponse");
		synchronized (day) {
			logger.debug("inside processResponse");
			weatherDTO
					.setCelsiusTemp((float) System.currentTimeMillis());
			weatherDTO
					.setFarenheitTemp((float) System.currentTimeMillis());
			weatherDTO
					.setConditions(String.valueOf(System
							.currentTimeMillis()
							+ day.getRelativeDay()));
		}
	}
	
	private String downloadWeatherUsingHTTP() throws RemoteException {
		String response = httpProcessor.getFeed(URL);
		return response;
	}
	
	public WeatherDTO getWeather() {
		synchronized (day) {
			return new WeatherDTO(weatherDTO);
		}
	}
}
