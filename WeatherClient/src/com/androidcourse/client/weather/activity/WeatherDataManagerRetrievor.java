package com.androidcourse.client.weather.activity;

import com.androidcourse.client.weather.processor.WeatherDataManager;
import com.arya.androidcourse.service.http.IHttpService;

/*
 * This provides the WeatherDataManager to the WeatherActivity.
 */

class WeatherDataManagerRetrievor {
	WeatherDataManager weatherDataManager = null;
	static WeatherDataManagerRetrievor weatherDataProviderRetrievor;
	
	static WeatherDataManagerRetrievor getInstance() {
		if (weatherDataProviderRetrievor == null) {
			weatherDataProviderRetrievor = new WeatherDataManagerRetrievor();
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
