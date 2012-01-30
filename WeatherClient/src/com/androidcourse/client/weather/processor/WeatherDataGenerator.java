package com.androidcourse.client.weather.processor;

import android.os.RemoteException;
import android.util.Log;

import com.androidcourse.client.weather.data.WeatherDTO;
import com.arya.androidcourse.service.http.IHttpService;

class WeatherDataGenerator implements Runnable {
	final String TAG = "WeatherDataGenerator ";
	
	final WeatherDTO weatherDTO;
	final WeatherDays day;
	final IHttpService httpService;
	final String URL = "http://google.com";
	
	WeatherDataGenerator(IHttpService httpService, WeatherDays day) {
		weatherDTO = new WeatherDTO();
		this.day = day;
		this.httpService = httpService;
		if (httpService == null) {
			Log.e(TAG, "httpService is null..cannot execute normally ");
		}
	}
	
	@Override
	public void run() {
		try {
			String response = downloadWeatherUsingHTTP();
			processResponse(response);
		}
		catch (RemoteException e) {
			Log.e(TAG, "RemoteException encountered : " + e);
		}
	}
	
	private void processResponse(String response) {
		Log.d(TAG, "outside synch processResponse");
		synchronized (day) {
			Log.d(TAG, "inside processResponse");
			weatherDTO
					.setCelsiusTemp((float) System.currentTimeMillis());
			weatherDTO
					.setFarenheitTemp((float) System.currentTimeMillis());
			weatherDTO
					.setConditions(String.valueOf(System
							.currentTimeMillis()
							+ day.getRelativeDay()));
		}
	}
	
	private String downloadWeatherUsingHTTP() throws RemoteException {
		String response = httpService.getFeed(URL);
		return response;
	}
	
	public WeatherDTO getWeather() {
		synchronized (day) {
			return new WeatherDTO(weatherDTO);
		}
	}
}
