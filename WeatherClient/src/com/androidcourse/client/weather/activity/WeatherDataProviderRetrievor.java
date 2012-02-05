package com.androidcourse.client.weather.activity;

import com.androidcourse.client.weather.processor.WeatherDataManager;
import com.arya.androidcourse.service.http.IHttpService;

class WeatherDataProviderRetrievor {
	WeatherDataManager weatherDataManager = null;
	static WeatherDataProviderRetrievor weatherDataProviderRetrievor;
	
	static WeatherDataProviderRetrievor getInstance() {
		if (weatherDataProviderRetrievor == null) {
			weatherDataProviderRetrievor = new WeatherDataProviderRetrievor();
		}
		return weatherDataProviderRetrievor;
	}
	
	WeatherDataManager getWeatherDataProvider(IHttpService httpService,
			String zipCode) {
		if (weatherDataManager == null) {
			weatherDataManager = WeatherDataManager.getInstance(httpService);
		}
		weatherDataManager.initiateWeatherDownload(zipCode);
		return weatherDataManager;
	}
	
	void shutDownWeatherDataProvider() throws InterruptedException {
		weatherDataManager.shutdown();
	}
	
}
