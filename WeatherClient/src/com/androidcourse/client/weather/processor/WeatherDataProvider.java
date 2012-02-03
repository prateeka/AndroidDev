package com.androidcourse.client.weather.processor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.androidcourse.client.weather.data.WeatherDTO;
import com.arya.androidcourse.service.http.IHttpService;

public class WeatherDataProvider {
	ExecutorService executor;
	WeatherDataGenerator[] weatherDataGenerators;
	Future<?>[] futures;
	final IHttpService httpService;
	static WeatherDataProvider weatherDataProvider;
	
	private WeatherDataProvider(IHttpService httpService) {
		initThreadExecutorService();
		this.httpService = httpService;
		initWeatherDataGeneratorArray();
	}
	
	public static WeatherDataProvider getInstance(IHttpService httpService) {
		if (weatherDataProvider == null) {
			weatherDataProvider = new WeatherDataProvider(httpService);
		}
		return weatherDataProvider;
	}
	
	protected void initThreadExecutorService() {
		final int corePoolSize = 4;
		executor = Executors.newFixedThreadPool(corePoolSize);
	}
	
	/*
	 * This method starts the weather data retrieval and ensure the latest
	 * weather data is retrieved as per below specified time delay.
	 */
	private void startWeatherDataGeneration() {
		futures = new Future[WeatherDays.values().length];
		for (int i = 0; i < futures.length; i++) {
			futures[i] = executor.submit(weatherDataGenerators[i]);
		}
	}
	
	protected void initWeatherDataGeneratorArray() {
		weatherDataGenerators = new WeatherDataGenerator[WeatherDays.values().length];
		int i = 0;
		for (WeatherDays day : WeatherDays.values()) {
			weatherDataGenerators[i] = new WeatherDataGenerator(
					httpService,
					day);
			i++;
		}
	}
	
	public WeatherDTO getWeather(WeatherDays day) {
		return weatherDataGenerators[day.getRelativeDay()].getWeather();
	}
	
	public void shutdown() throws InterruptedException {
		cancelFutures();
		executor.shutdownNow();
		final int timeout = 30;
		executor.awaitTermination(timeout, TimeUnit.SECONDS);
	}
	
	private void cancelFutures() {
		for (Future<?> future : futures) {
			future.cancel(true);
		}
	}
	
	public void initiateWeatherDownload(String zipCode) {
		setZipCodeIntoWeatherDataGenerator(zipCode);
		startWeatherDataGeneration();
	}
	
	void setZipCodeIntoWeatherDataGenerator(String zipCode) {
		for (WeatherDataGenerator weatherDataGenerator : weatherDataGenerators) {
			weatherDataGenerator.setZipcode(zipCode);
		}
	}
}
