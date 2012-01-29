package com.androidcourse.client.weather.processor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.androidcourse.client.weather.data.WeatherDTO;

public class WeatherDataProvider {
	ScheduledExecutorService scheduler;
	WeatherDataGenerator[] weatherDataGenerator;
	ScheduledFuture[] future;
	
	public WeatherDataProvider() {
		initWeatherDataGeneratorArray();
		initScheduledExecutor();
		startWeatherDataGeneration();
	}
	
	protected void initScheduledExecutor() {
		final int corePoolSize = 4;
		scheduler = Executors.newScheduledThreadPool(corePoolSize);
	}
	
	/*
	 * This method starts the weather data retrieval and ensure the latest
	 * weather data is retrieved every 5 mins.
	 */
	private void startWeatherDataGeneration() {
		future = new ScheduledFuture[WeatherDays.values().length];
		final int delay = 5;
		for (int i = 0; i < future.length; i++) {
			future[i] = scheduler.scheduleWithFixedDelay(
					weatherDataGenerator[i],
					0, delay, TimeUnit.MINUTES);
		}
	}
	
	protected void initWeatherDataGeneratorArray() {
		weatherDataGenerator = new WeatherDataGenerator[WeatherDays.values().length];
		for (int i = 0; i < weatherDataGenerator.length; i++) {
			weatherDataGenerator[i] = new WeatherDataGenerator();
		}
	}
	
	public WeatherDTO getWeather(WeatherDays day) {
		return weatherDataGenerator[day.getRelativeDay()].getWeather();
	}
}
