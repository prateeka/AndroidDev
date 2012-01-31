package com.androidcourse.client.weather.processor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.androidcourse.client.weather.data.WeatherDTO;
import com.androidcourse.client.weather.http.HttpProcessor;

public class WeatherDataProvider {
	private static final int REFRESH_INTERVAL = 5000; // It is millisec
	ScheduledExecutorService scheduler;
	WeatherDataGenerator[] weatherDataGenerator;
	ScheduledFuture<?>[] futures;
	
	public WeatherDataProvider(HttpProcessor httpProcessor) {
		initWeatherDataGeneratorArray(httpProcessor);
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
		final int delay = REFRESH_INTERVAL;
		for (int i = 0; i < futures.length; i++) {
			futures[i] = scheduler.scheduleWithFixedDelay(
					weatherDataGenerator[i],
					0, delay, TimeUnit.MILLISECONDS);
		}
	}
	
	protected void initWeatherDataGeneratorArray(HttpProcessor httpProcessor) {
		weatherDataGenerator = new WeatherDataGenerator[WeatherDays.values().length];
		int i = 0;
		for (WeatherDays day : WeatherDays.values()) {
			weatherDataGenerator[i] = new WeatherDataGenerator(
					httpProcessor,
					day);
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
