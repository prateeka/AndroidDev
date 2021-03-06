package com.androidcourse.client.weather.processor;

import android.os.RemoteException;
import android.util.Log;

import com.androidcourse.client.weather.data.WeatherDTO;
import com.arya.androidcourse.service.http.IHttpService;

/*
 * This class is mainly responsible for providing the weather data to be
 * displayed by the Async tasks. It co-ordinates with WeatherDataGenerator to
 * generate the weather data. Each instance of this class runs in a different
 * thread such that each instance is responsible for downloading data for one
 * day.
 */

class WeatherDataProvider implements Runnable {
	final String TAG = "WeatherDataProvider";
	WeatherDTO weatherDTO;
	final WeatherDays day;
	final WeatherDataGenerator weatherDataGenerator;
	
	WeatherDataProvider(IHttpService httpService, WeatherDays day) {
		this.day = day;
		weatherDataGenerator = new WeatherDataGenerator(httpService, day);
		if (httpService == null) {
			Log.e(TAG, "httpService is null..cannot execute normally ");
		}
	}
	
	@Override
	public void run() {
		synchronized (this) {
			try {
				WeatherDTO lWeatherDTO = weatherDataGenerator.getWeatherDTO();
				synchronized (day) {
					weatherDTO = lWeatherDTO;
				}
			}
			catch (RemoteException e) {
				Log.e(TAG, "RemoteException encountered : " + e);
			}
		}
	}
	
	public WeatherDTO getWeather() {
		WeatherDTO lWeatherDTO = null;
		synchronized (day) {
			if (weatherDTO != null) {
				lWeatherDTO = WeatherDTO.getCopy(weatherDTO);
			} else {
				lWeatherDTO = WeatherDTO.getInstance();
			}
		}
		return lWeatherDTO;
	}
	
	void setZipCode(String zipCode) {
		synchronized (this) {
			weatherDataGenerator.setZipCode(zipCode);
			resetWeatherDTO();
		}
	}
	
	void resetWeatherDTO() {
		synchronized (day) {
			if (weatherDTO != null) {
				weatherDTO.reset();
			}
		}
	}
}