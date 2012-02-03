package com.androidcourse.client.weather.processor;

import org.json.JSONException;

import android.os.RemoteException;
import android.util.Log;

import com.androidcourse.client.weather.data.WeatherDTO;
import com.arya.androidcourse.service.http.IHttpService;

class WeatherDataGenerator implements Runnable {
	final String TAG = "WeatherDataGenerator";
	WeatherDTO weatherDTO;
	final WeatherDays day;
	final IHttpService httpService;
	String zipCode = null;
	
	WeatherDataGenerator(IHttpService httpService, WeatherDays day) {
		this.day = day;
		this.httpService = httpService;
		if (httpService == null) {
			Log.e(TAG, "httpService is null..cannot execute normally ");
		}
	}
	
	@Override
	public void run() {
		synchronized (this) {
			try {
				String response = downloadWeatherUsingHTTP();
				processResponse(response);
			}
			catch (RemoteException e) {
				Log.e(TAG, "RemoteException encountered : " + e);
			}
			catch (JSONException e) {
				Log.e(TAG, "JSONException encountered : " + e);
			}
		}
	}
	
	private void processResponse(String response) throws JSONException {
		WeatherDTO lWeatherDTO = null;
		WeatherJSONParser jsonParser = WeatherJSONParser.getInstance();
		if (day == WeatherDays.TODAY) {
			lWeatherDTO = jsonParser.parseWeather(response, day);
		}
		else {
			lWeatherDTO = jsonParser.parseWeather(response, day);
		}
		
		synchronized (day) {
			weatherDTO = lWeatherDTO;
		}
	}
	
	private String downloadWeatherUsingHTTP() throws RemoteException {
		String url;
		if (day == WeatherDays.TODAY) {
			url = "http://api.wunderground.com/api/b3a987070762aec0/conditions/q/"
					+ zipCode + ".json";
		} else {
			url = "http://api.wunderground.com/api/b3a987070762aec0/forecast/q/"
					+ zipCode + ".json";
		}
		String response = httpService.getFeed(url);
		return response;
	}
	
	public WeatherDTO getWeather() {
		WeatherDTO lWeatherDTO = null;
		synchronized (day) {
			if (weatherDTO != null) {
				lWeatherDTO = new WeatherDTO(weatherDTO);
			} else {
				lWeatherDTO = new WeatherDTO();
			}
		}
		return lWeatherDTO;
	}
	
	void setZipcode(String zipCode) {
		synchronized (this) {
			this.zipCode = zipCode;
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