package com.androidcourse.client.weather.activity;

import com.androidcourse.client.weather.processor.WeatherDataProvider;
import com.arya.androidcourse.service.http.IHttpService;

class WeatherDataProviderRetrievor {
	WeatherDataProvider weatherDataProvider = null;
	static WeatherDataProviderRetrievor weatherDataProviderRetrievor;
	
	static WeatherDataProviderRetrievor getInstance() {
		if (weatherDataProviderRetrievor == null) {
			weatherDataProviderRetrievor = new WeatherDataProviderRetrievor();
		}
		return weatherDataProviderRetrievor;
	}
	
	WeatherDataProvider getWeatherDataProvider(IHttpService httpService,
			String zipCode) {
		if (weatherDataProvider == null) {
			weatherDataProvider = WeatherDataProvider.getInstance(httpService);
		}
		weatherDataProvider.initiateWeatherDownload(zipCode);
		return weatherDataProvider;
	}
	
	void shutDownWeatherDataProvider() throws InterruptedException {
		weatherDataProvider.shutdown();
	}
	
}
