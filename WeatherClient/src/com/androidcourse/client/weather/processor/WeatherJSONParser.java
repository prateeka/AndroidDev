package com.androidcourse.client.weather.processor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

import com.androidcourse.client.weather.data.WeatherDTO;

class WeatherJSONParser {
	final String TAG = "WeatherJSONParser";
	
	WeatherDTO parseCurrentConditions(String stringtoParse)
			throws JSONException {
		
		JSONObject jsonObject = getJSONObject(stringtoParse);
		
		final String CURRENT_OBSERVATION = "current_observation";
		final String TEMP_F = "temp_f";
		final String TEMP_C = "temp_c";
		final String CONDITIONS = "weather";
		
		jsonObject = jsonObject.getJSONObject(CURRENT_OBSERVATION);
		String temp_f = jsonObject.getString(TEMP_F);
		String temp_c = jsonObject.getString(TEMP_C);
		String conditions = jsonObject.getString(CONDITIONS);
		
		logValues(temp_f, temp_c, conditions);
		return new WeatherDTO(temp_c, temp_f, conditions);
	}
	
	protected void logValues(String temp_f, String temp_c, String conditions) {
		Log.d(TAG, "value for temp_f is " + temp_f);
		Log.d(TAG, "value for temp_c is " + temp_c);
		Log.d(TAG, "value for conditions is " + conditions);
	}
	
	WeatherDTO parseForecastConditions(String stringtoParse)
			throws JSONException {
		JSONObject jsonObject = getJSONObject(stringtoParse);
		
		final String FORECAST = "forecast";
		final String SIMPLEFORECAST = "simpleforecast";
		final String FORECASTDAY = "forecastday";
		final String HIGH = "high";
		final String FAHRENHEIT = "fahrenheit";
		final String CELSIUS = "celsius";
		final String CONDITIONS = "conditions";
		
		jsonObject = jsonObject.getJSONObject(FORECAST);
		jsonObject = jsonObject.getJSONObject(SIMPLEFORECAST);
		JSONArray jsonArray = jsonObject.getJSONArray(FORECASTDAY);
		jsonObject = jsonArray.getJSONObject(1);
		JSONObject cjsonObject = jsonObject.getJSONObject(HIGH);
		
		String temp_f = cjsonObject.getString(FAHRENHEIT);
		String temp_c = cjsonObject.getString(CELSIUS);
		String conditions = jsonObject.getString(CONDITIONS);
		
		logValues(temp_f, temp_c, conditions);
		return new WeatherDTO(temp_c, temp_f, conditions);
	}
	
	protected JSONObject getJSONObject(String stringtoParse)
			throws JSONException {
		
		return (JSONObject) new JSONTokener(stringtoParse)
				.nextValue();
	}
}
