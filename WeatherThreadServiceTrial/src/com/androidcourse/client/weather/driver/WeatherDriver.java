package com.androidcourse.client.weather.driver;

import com.androidcourse.client.weather.data.WeatherDTO;
import com.androidcourse.client.weather.processor.WeatherDataProvider;
import com.androidcourse.client.weather.processor.WeatherDays;

public class WeatherDriver {
	final WeatherDataProvider weatherDataProvider;
	
	WeatherDriver() {
		weatherDataProvider = new WeatherDataProvider();
	}
	
	public static void main(String args[]) throws InterruptedException {
		WeatherDriver driver = new WeatherDriver();
		for (int i = 0; i < 100; i++) {
			driver.printData(WeatherDays.TODAY);
			driver.printData(WeatherDays.TODAY_PLUS_1);
			Thread.sleep(1000);
		}
		driver.shutdownThread();
	}
	
	private void shutdownThread() throws InterruptedException {
		weatherDataProvider.shutdown();
	}
	
	private void printData(WeatherDays day) {
		WeatherDTO weatherDTO;
		synchronized (day) {
			weatherDTO = weatherDataProvider.getWeather(day);
			if (weatherDTO != null) {
				if (weatherDTO.isValid()) {
					System.out.println("weatherDTO for :" + day + " is "
							+ weatherDTO);
				} else {
					System.out.println("weatherDTO for :" + day
							+ " is invalid ");
				}
			}
		}
	}
}
