package com.androidcourse.client.weather.data;

public class WeatherDTO {
	Float celsiusTemp;
	Float farenheitTemp;
	String conditions;
	
	public WeatherDTO() {
	}
	
	public boolean isValid() {
		if ((celsiusTemp != null) && (farenheitTemp != null)
				&& (conditions != null) && (conditions.trim() != "")) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setCelsiusTemp(Float celsiusTemp) {
		this.celsiusTemp = celsiusTemp;
	}
	
	public void setFarenheitTemp(Float farenheitTemp) {
		this.farenheitTemp = farenheitTemp;
	}
	
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
}
