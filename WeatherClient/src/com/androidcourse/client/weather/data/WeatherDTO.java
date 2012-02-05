package com.androidcourse.client.weather.data;

public class WeatherDTO {
	String celsiusTemp;
	String farenheitTemp;
	String conditions;
	State state;
	
	private WeatherDTO(String celsiusTemp, String farenheitTemp,
			String conditions, State state) {
		this.celsiusTemp = celsiusTemp;
		this.farenheitTemp = farenheitTemp;
		this.conditions = conditions;
		this.state = state;
	}
	
	private WeatherDTO(WeatherDTO weatherDTO) {
		this(
				weatherDTO.celsiusTemp,
				weatherDTO.farenheitTemp,
				weatherDTO.conditions,
				weatherDTO.state);
	}
	
	public static WeatherDTO getCopy(WeatherDTO weatherDTO) {
		return new WeatherDTO(weatherDTO);
	}
	
	public static WeatherDTO getInstance() {
		return new WeatherDTO(null, null, null, State.INACTIVE);
	}
	
	public static WeatherDTO getInstance(String celsiusTemp,
			String farenheitTemp,
			String conditions, State state) {
		return new WeatherDTO(celsiusTemp, farenheitTemp, conditions, state);
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
				+ farenheitTemp + ", conditions=" + conditions + ", state="
				+ state + "]";
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
	}
	
	public State getState() {
		return state;
	}
}
