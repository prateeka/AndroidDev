package com.androidcourse.client.weather.processor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
import android.util.Log;

import com.androidcourse.client.weather.data.State;
import com.androidcourse.client.weather.data.WeatherDTO;
import com.arya.androidcourse.service.http.IHttpService;
import com.arya.androidcourse.service.http.ParseableByteArray;

class WeatherDataGenerator {
	
	final IHttpService httpService;
	final WeatherDays day;
	String zipCode = null;
	final String TAG = "WeatherDataGenerator";
	
	WeatherDataGenerator(IHttpService httpService, WeatherDays day) {
		this.httpService = httpService;
		this.day = day;
	}
	
	WeatherDTO getWeatherDTO() throws RemoteException {
		WeatherDTO weatherDTO = downloadTempAndConditions();
		if ((weatherDTO.getState() != null)
				&& (weatherDTO.getState() == State.TEXT_DOWNLOADED)) {
			Log.d(TAG, "weatherDTO before downloadConditionsImage is "
					+ weatherDTO);
			downloadConditionsImage(weatherDTO);
			weatherDTO.setState(State.READY);
			Log.d(TAG, "weatherDTO after downloadConditionsImage is "
					+ weatherDTO);
		}
		return weatherDTO;
	}
	
	protected void downloadConditionsImage(WeatherDTO weatherDTO)
			throws RemoteException {
		Log.d(TAG, "beginning downloadConditionsImage");
		ParseableByteArray parByteArray = downloadImage(weatherDTO.getIconURL());
		Bitmap bitmap = convertParseableByteArrayToBitMap(parByteArray);
		weatherDTO.setBitmap(bitmap);
		Log.d(TAG, "returning from downloadConditionsImage");
	}
	
	private Bitmap convertParseableByteArrayToBitMap(
			ParseableByteArray parByteArray) {
		Bitmap bitmap = BitmapFactory.decodeByteArray(
				parByteArray.getByteArray(),
				0,
				parByteArray.getByteArray().length);
		Log.d(TAG, "returning from convertParseableByteArrayToBitMap");
		return bitmap;
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
	
	private ParseableByteArray downloadImage(String url) throws RemoteException {
		Log.d(TAG, "beginning downloadImage for url : " + url);
		ParseableByteArray parByteArray = httpService.getImageContent(url);
		Log.d(TAG, "returning from downloadImage for url : " + url);
		return parByteArray;
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
