package com.androidcourse.client.weather.data;

public class WeatherDTO {
	String celsiusTemp;
	String farenheitTemp;
	String conditions;
	
	public WeatherDTO(String celsiusTemp, String farenheitTemp,
			String conditions) {
		this.celsiusTemp = celsiusTemp;
		this.farenheitTemp = farenheitTemp;
		this.conditions = conditions;
	}
	
	public WeatherDTO(WeatherDTO weatherDTO) {
		this(
				weatherDTO.celsiusTemp,
				weatherDTO.farenheitTemp,
				weatherDTO.conditions);
	}
	
	public WeatherDTO() {
		this(null, null, null);
	}
	
	public boolean isValid() {
		if (isNonNullAndNonEmpty(celsiusTemp)
				&& isNonNullAndNonEmpty(farenheitTemp)
				&& isNonNullAndNonEmpty(conditions)) {
			return true;
		} else {
			return false;
		}
	}
	
	protected boolean isNonNullAndNonEmpty(String param) {
		return (param != null) && (param.trim().length() != 0);
	}
	
	public void setCelsiusTemp(String celsiusTemp) {
		this.celsiusTemp = celsiusTemp;
	}
	
	public void setFarenheitTemp(String farenheitTemp) {
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
	}
}
