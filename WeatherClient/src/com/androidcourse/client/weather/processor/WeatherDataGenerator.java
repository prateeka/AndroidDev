package com.androidcourse.client.weather.processor;

import android.os.RemoteException;

import com.androidcourse.client.weather.data.State;
import com.androidcourse.client.weather.data.WeatherDTO;
import com.arya.androidcourse.service.http.IHttpService;

class WeatherDataGenerator {
	
	final IHttpService httpService;
	final WeatherDays day;
	String zipCode = null;
	
	WeatherDataGenerator(IHttpService httpService, WeatherDays day) {
		this.httpService = httpService;
		this.day = day;
	}
	
	WeatherDTO getWeatherDTO() throws RemoteException {
		WeatherDTO weatherDTO = downloadTempAndConditions();
		if (weatherDTO.getState() == State.DOWNLOADING) {
			downloadConditionsImage(weatherDTO);
		}
		return weatherDTO;
	}
	
	protected WeatherDTO downloadConditionsImage(WeatherDTO weatherDTO) {
		// String response = downloadWeatherUsingHTTP();
		return null;
	}
	
	protected WeatherDTO downloadTempAndConditions() throws RemoteException {
		String url = generateURLForTempAndConditions();
		String response = downloadData(url);
		WeatherDTO weatherDTO = processResponse(response);
		return weatherDTO;
	}
	
	private String downloadData(String url) throws RemoteException {
		String response = httpService.getTextContent(url);
		return response;
	}
	
	protected String generateURLForTempAndConditions() {
		String url;
		if (day == WeatherDays.TODAY) {
			url = "http://api.wunderground.com/api/b3a987070762aec0/conditions/q/"
					+ zipCode + ".json";
		} else {
			url = "http://api.wunderground.com/api/b3a987070762aec0/forecast/q/"
					+ zipCode + ".json";
		}
		return url;
	}
	
	private WeatherDTO processResponse(String response) {
		WeatherDTO lWeatherDTO = null;
		WeatherJSONParser jsonParser = WeatherJSONParser.getInstance();
		if (day == WeatherDays.TODAY) {
			lWeatherDTO = jsonParser.parseWeather(response, day);
		}
		else {
			lWeatherDTO = jsonParser.parseWeather(response, day);
		}
		return lWeatherDTO;
	}
	
	void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
}
