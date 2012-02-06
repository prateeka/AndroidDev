package com.androidcourse.client.weather.data;

import android.graphics.Bitmap;

public class WeatherDTO {
	String celsiusTemp;
	String farenheitTemp;
	String conditions;
	State state;
	String iconURL;
	Bitmap bitmap;
	
	private WeatherDTO(String celsiusTemp, String farenheitTemp,
			String conditions, State state, String iconURL, Bitmap bitmap) {
		this.celsiusTemp = celsiusTemp;
		this.farenheitTemp = farenheitTemp;
		this.conditions = conditions;
		this.state = state;
		this.iconURL = iconURL;
		this.bitmap = bitmap;
	}
	
	private WeatherDTO(WeatherDTO weatherDTO) {
		this(
				weatherDTO.celsiusTemp,
				weatherDTO.farenheitTemp,
				weatherDTO.conditions,
				weatherDTO.state,
				weatherDTO.iconURL,
				weatherDTO.bitmap);
	}
	
	public static WeatherDTO getCopy(WeatherDTO weatherDTO) {
		return new WeatherDTO(weatherDTO);
	}
	
	public static WeatherDTO getInstance() {
		return new WeatherDTO(null, null, null, State.INACTIVE, null, null);
	}
	
	public static WeatherDTO getInstance(String celsiusTemp,
			String farenheitTemp,
			String conditions,
			State state,
			String iconURL,
			Bitmap bitmap) {
		return new WeatherDTO(
				celsiusTemp,
				farenheitTemp,
				conditions,
				state,
				iconURL,
				bitmap);
	}
	
	public String getCelsiusTemp() {
		return celsiusTemp;
	}
	
	public String getFarenheitTemp() {
		return farenheitTemp;
	}
	
	public String getConditions() {
		return conditions;
	}
	
	public void reset() {
		celsiusTemp = null;
		farenheitTemp = null;
		conditions = null;
		state = State.INACTIVE;
		iconURL = null;
		bitmap = null;
	}
	
	public State getState() {
		return state;
	}
	
	public String getIconURL() {
		return iconURL;
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	@Override
	public String toString() {
		return "WeatherDTO [celsiusTemp=" + celsiusTemp + ", farenheitTemp="
				+ farenheitTemp + ", conditions=" + conditions + ", state="
				+ state + ", iconURL=" + iconURL + ", bitmap=" + bitmap + "]";
	}
	
}
