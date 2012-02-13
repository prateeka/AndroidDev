package com.androidcourse.client.weather.processor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.androidcourse.client.weather.data.WeatherDTO;
import com.arya.androidcourse.service.http.IHttpService;

/*
 * This class is responsible for initiating 4 different threads of weather
 * download with each thread responsible for downloading data for one day.
 * It co-ordinates with WeatherDataProviders to download the weather data and
 * later WeatherDataProviders provide the Weather data to Async tasks to be
 * displayed.
 */

public class WeatherDataManager {
	ExecutorService executor;
	WeatherDataProvider[] weatherDataProviders;
	Future<?>[] futures;
	final IHttpService httpService;
	static WeatherDataManager weatherDataManager;
	
	private WeatherDataManager(IHttpService httpService) {
		initThreadExecutorService();
		this.httpService = httpService;
		initWeatherDataGeneratorArray();
	}
	
	public static WeatherDataManager getInstance(IHttpService httpService) {
		if (weatherDataManager == null) {
			weatherDataManager = new WeatherDataManager(httpService);
		}
		return weatherDataManager;
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
			futures[i] = executor.submit(weatherDataProviders[i]);
		}
	}
	
	protected void initWeatherDataGeneratorArray() {
		weatherDataProviders = new WeatherDataProvider[WeatherDays.values().length];
		int i = 0;
		for (WeatherDays day : WeatherDays.values()) {
			weatherDataProviders[i] = new WeatherDataProvider(
					httpService,
					day);
			i++;
		}
	}
	
	public WeatherDTO getWeather(WeatherDays day) {
		return weatherDataProviders[day.getRelativeDay()].getWeather();
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
		for (WeatherDataProvider weatherDataProvider : weatherDataProviders) {
			weatherDataProvider.setZipCode(zipCode);
		}
	}
}
