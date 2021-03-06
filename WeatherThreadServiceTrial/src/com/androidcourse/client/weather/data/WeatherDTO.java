package com.androidcourse.client.weather.data;

public class WeatherDTO {
	Float celsiusTemp;
	Float farenheitTemp;
	String conditions;
	
	public WeatherDTO() {
	}
	
	public WeatherDTO(WeatherDTO weatherDTO) {
		celsiusTemp = weatherDTO.celsiusTemp;
		farenheitTemp = weatherDTO.farenheitTemp;
		conditions = weatherDTO.conditions;
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
	
	@Override
	public String toString() {
		return "WeatherDTO [celsiusTemp=" + celsiusTemp + ", farenheitTemp="
				+ farenheitTemp + ", conditions=" + conditions + "]";
	}
	
	public Float getCelsiusTemp() {
		return celsiusTemp;
	}
	
	public Float getFarenheitTemp() {
		return farenheitTemp;
	}
	
	public String getConditions() {
		return conditions;
	}
}
