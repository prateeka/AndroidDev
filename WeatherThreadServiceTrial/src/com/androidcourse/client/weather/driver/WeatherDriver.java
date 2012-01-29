package com.androidcourse.client.weather.driver;

import com.androidcourse.client.weather.data.WeatherDTO;
import com.androidcourse.client.weather.processor.WeatherDataProvider;
import com.androidcourse.client.weather.processor.WeatherDays;

public class WeatherDriver {
	final WeatherDataProvider processor;
	
	WeatherDriver() {
		processor = new WeatherDataProvider();
	}
	
	public static void main(String args[]) {
		WeatherDriver driver = new WeatherDriver();
		driver.printData(WeatherDays.TODAY);
		driver.printData(WeatherDays.TODAY_PLUS_1);
	}
	
	private void printData(WeatherDays day) {
		WeatherDTO weatherDTO;
		synchronized (day) {
			weatherDTO = processor.getWeather(day);
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
