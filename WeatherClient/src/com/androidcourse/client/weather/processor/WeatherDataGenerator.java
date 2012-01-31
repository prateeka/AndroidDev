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
	
	WeatherDataGenerator(IHttpService httpService, WeatherDays day) {
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
			weatherDTO = processResponse(response);
		}
		catch (RemoteException e) {
			Log.e(TAG, "RemoteException encountered : " + e);
		}
		catch (JSONException e) {
			Log.e(TAG, "JSONException encountered : " + e);
		}
	}
	
	private WeatherDTO processResponse(String response) throws JSONException {
		Log.d(TAG, "outside synch processResponse");
		synchronized (day) {
			Log.d(TAG, "inside processResponse");
			WeatherJSONParser jsonParser = new WeatherJSONParser();
			WeatherDTO weatherDTO = jsonParser.parseCurrentConditions(response);
			return weatherDTO;
		}
	}
	
	private String downloadWeatherUsingHTTP() throws RemoteException {
		String url;
		if (day == WeatherDays.TODAY) {
			url = "http://api.wunderground.com/api/b3a987070762aec0/conditions/q/98105.json";
		} else {
			url = "http://api.wunderground.com/api/b3a987070762aec0/forecast/q/98105.json";
		}
		String response = httpService.getFeed(url);
		return response;
	}
	
	public WeatherDTO getWeather() {
		synchronized (day) {
			return new WeatherDTO(weatherDTO);
		}
	}
}
