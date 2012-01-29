package com.androidcourse.client.weather.processor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.androidcourse.client.weather.data.WeatherDTO;

public class WeatherDataProvider {
	ScheduledExecutorService scheduler;
	WeatherDataGenerator[] weatherDataGenerator;
	ScheduledFuture<?>[] futures;
	
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
	 * weather data is retrieved as per below specified time delay.
	 */
	private void startWeatherDataGeneration() {
		futures = new ScheduledFuture[WeatherDays.values().length];
		final int delay = 1;
		for (int i = 0; i < futures.length; i++) {
			futures[i] = scheduler.scheduleWithFixedDelay(
					weatherDataGenerator[i],
					0, delay, TimeUnit.SECONDS);
		}
	}
	
	protected void initWeatherDataGeneratorArray() {
		weatherDataGenerator = new WeatherDataGenerator[WeatherDays.values().length];
		int i = 0;
		for (WeatherDays day : WeatherDays.values()) {
			weatherDataGenerator[i] = new WeatherDataGenerator(day);
			i++;
		}
	}
	
	public WeatherDTO getWeather(WeatherDays day) {
		return weatherDataGenerator[day.getRelativeDay()].getWeather();
	}
	
	public void shutdown() throws InterruptedException {
		cancelFutures();
		scheduler.shutdownNow();
		final int timeout = 30;
		scheduler.awaitTermination(timeout, TimeUnit.SECONDS);
	}
	
	private void cancelFutures() {
		for (ScheduledFuture<?> future : futures) {
			future.cancel(true);
		}
	}
}
