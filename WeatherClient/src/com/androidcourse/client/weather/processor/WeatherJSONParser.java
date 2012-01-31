package com.androidcourse.client.weather.processor;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

import com.androidcourse.client.weather.data.WeatherDTO;

class WeatherJSONParser {
	final String TAG = "WeatherJSONParser";
	
	WeatherDTO parseCurrentConditions(String stringtoParse)
			throws JSONException {
		
		JSONObject jSONObject = (JSONObject) new JSONTokener(stringtoParse)
				.nextValue();
		
		final String CURRENT_OBSERVATION = "current_observation";
		final String TEMP_F = "temp_f";
		final String TEMP_C = "temp_c";
		final String CONDITIONS = "weather";
		
		jSONObject = jSONObject.getJSONObject(CURRENT_OBSERVATION);
		String temp_f = jSONObject.getString(TEMP_F);
		String temp_c = jSONObject.getString(TEMP_C);
		String conditions = jSONObject.getString(CONDITIONS);
		
		Log.d(TAG, "value for temp_f is " + temp_f);
		Log.d(TAG, "value for temp_c is " + temp_c);
		Log.d(TAG, "value for conditions is " + conditions);
		return new WeatherDTO(temp_c, temp_f, conditions);
	}
}
