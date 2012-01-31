package com.androidcourse.client.weather.activity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.androidcourse.client.weather.data.WeatherDTO;
import com.androidcourse.client.weather.processor.WeatherDataProvider;
import com.androidcourse.client.weather.processor.WeatherDays;
import com.arya.androidcourse.service.http.IHttpService;

public class DownloadWeatherTask extends AsyncTask<String, Integer, WeatherDTO> {
	private final String TAG = "DownloadWeatherTask";
	
	private final WeatherDataProvider weatherDataProvider;
	private WeatherDTO weatherDTO;
	private TextView conditionsText;
	private TextView celsiusTempText;
	private TextView farenTempText;
	final WeatherDays day;
	
	DownloadWeatherTask(Context context, IHttpService httpService,
			WeatherDays day, int zip) {
		initViews(context);
		weatherDataProvider = new WeatherDataProvider(httpService);
		this.day = day;
	}
	
	// Called from main thread to re-attach
	protected void setContext(Context context) {
		Log.v(TAG, "setContext called");
		initViews(context);
		if (getStatus() == AsyncTask.Status.FINISHED) {
			displayWeatherData();
		} else {
			displayDownloadingMsg();
		}
	}
	
	protected void initViews(Context context) {
		conditionsText = (TextView)
				((Activity) context).findViewById(R.id.conditions);
		celsiusTempText = (TextView)
				((Activity) context).findViewById(R.id.celsiusTemp);
		farenTempText = (TextView)
				((Activity) context).findViewById(R.id.farenTemp);
	}
	
	@Override
	protected void onPreExecute() {
		displayDownloadingMsg();
	}
	
	@Override
	protected WeatherDTO doInBackground(String... urls) {
		Log.d(TAG, "initiating downloading weather data");
		return downloadWeatherData();
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress) {
	}
	
	@Override
	protected void onPostExecute(WeatherDTO result) {
		if (result != null) {
			weatherDTO = result;
			displayWeatherData();
		}
		else {
			displayErrorMsg();
		}
	}
	
	private void displayErrorMsg() {
		final String ERROR_MSG = "Problem downloading weather data. Please try later";
		setTextMsg(conditionsText, ERROR_MSG);
		setTextMsg(celsiusTempText, ERROR_MSG);
		setTextMsg(farenTempText, ERROR_MSG);
	}
	
	protected void displayDownloadingMsg() {
		final String DOWNLOADING_DATA = "Downloading Data";
		setTextMsg(conditionsText, DOWNLOADING_DATA);
		setTextMsg(celsiusTempText, DOWNLOADING_DATA);
		setTextMsg(farenTempText, DOWNLOADING_DATA);
	}
	
	protected void displayWeatherData() {
		if (weatherDTO != null) {
			setTextMsg(conditionsText, weatherDTO.getConditions());
			setTextMsg(celsiusTempText, weatherDTO.getCelsiusTemp().toString());
			setTextMsg(farenTempText, weatherDTO.getFarenheitTemp().toString());
		}
	}
	
	protected void setTextMsg(TextView view, String msg) {
		view.setText(msg);
	}
	
	public void shutdown() throws InterruptedException {
		weatherDataProvider.shutdown();
	}
	
	private WeatherDTO downloadWeatherData() {
		WeatherDTO weatherDTO = null;
		final int INVALID_SLEEP_INTERVAL = 5000;
		
		while (true) {
			if ((weatherDTO != null) && weatherDTO.isValid()) {
				Log.d(TAG, "weatherDTO for :" + day + " is valid as : "
						+ weatherDTO);
				break;
			}
			else {
				Log.v(TAG, "weatherDTO for :" + day
						+ " is invalid : " + weatherDTO);
				weatherDTO = weatherDataProvider.getWeather(day);
			}
			sleepFor(INVALID_SLEEP_INTERVAL);
		}
		return weatherDTO;
	}
	
	private void sleepFor(long msecs) {
		try {
			Thread.sleep(msecs);
		}
		catch (InterruptedException e) {
			Log.e(TAG, "sleep interrupted");
		}
	}
}
