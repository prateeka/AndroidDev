package com.androidcourse.client.weather.activity;

import java.util.HashMap;
import java.util.Map;

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
	private static final String TAG = "DownloadWeatherTask";
	
	private WeatherDTO weatherDTO;
	private TextView conditionsText;
	private TextView celsiusTempText;
	private TextView farenTempText;
	final WeatherDays day;
	final Integer index;
	static final String CONDITIONS = "conditions";
	static final String CELSIUSTEMP = "celsiusTemp";
	static final String FARENTEMP = "farenTemp";
	
	private final WeatherDataProvider weatherDataProvider;
	private static final Map<String, Integer> viewMap = new HashMap<String, Integer>();
	
	static {
		viewMap.put(CONDITIONS + "0", R.id.conditions0);
		viewMap.put(CELSIUSTEMP + "0", R.id.celsiusTemp0);
		viewMap.put(FARENTEMP + "0", R.id.farenTemp0);
		viewMap.put(CONDITIONS + "1", R.id.conditions1);
		viewMap.put(CELSIUSTEMP + "1", R.id.celsiusTemp1);
		viewMap.put(FARENTEMP + "1", R.id.farenTemp1);
		
		/*-		for (String key : viewMap.keySet()) {
		 Log.d(TAG, "key:value is " + key + ":" + viewMap.get(key));
		 }
		 */}
	
	DownloadWeatherTask(Context context, IHttpService httpService,
			WeatherDays day, int index, int zip) {
		this.day = day;
		this.index = index;
		weatherDataProvider = WeatherDataProvider.getInstance(httpService);
		initViews(context);
	}
	
	// Called from main thread to re-attach
	protected void setContext(Context context) {
		initViews(context);
		if (getStatus() == AsyncTask.Status.FINISHED) {
			displayWeatherData();
		} else {
			displayDownloadingMsg();
		}
	}
	
	protected void initViews(Context context) {
		conditionsText = (TextView)
				((Activity) context).findViewById(viewMap.get(CONDITIONS
						+ index));
		/*-		Log.d(TAG, "this:conditionsText for day  " + day + " is " + this + ":"
		 + conditionsText);
		 Log.d(TAG, "this:celsiusTempText for day " + day + " is " + this + ":"
		 + celsiusTempText);Log.d(TAG, "this:farenTempTextfor day " + day + " is " + this + ":"
		 + farenTempText);
		 */
		celsiusTempText = (TextView)
				((Activity) context).findViewById(viewMap.get(CELSIUSTEMP
						+ index));
		
		farenTempText = (TextView)
				((Activity) context).findViewById(viewMap.get(FARENTEMP
						+ index));
		
	}
	
	@Override
	protected void onPreExecute() {
		displayDownloadingMsg();
	}
	
	@Override
	protected WeatherDTO doInBackground(String... urls) {
		Log.d(TAG, "initiating downloading weather data for day  " + day);
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
			setTextMsg(celsiusTempText, weatherDTO.getCelsiusTemp());
			setTextMsg(farenTempText, weatherDTO.getFarenheitTemp());
		}
	}
	
	protected void setTextMsg(TextView view, String msg) {
		// Log.d(TAG, "view:msg is " + view + ":" + msg);
		view.setText(msg);
	}
	
	public void shutdown() throws InterruptedException {
		weatherDataProvider.shutdown();
	}
	
	private WeatherDTO downloadWeatherData() {
		WeatherDTO weatherDTO = null;
		final int INVALID_SLEEP_INTERVAL = 5000; // millisec
		
		while (true) {
			if ((weatherDTO != null) && weatherDTO.isValid()) {
				Log.d(TAG, "weatherDTO for :" + day + " is valid as : "
						+ weatherDTO);
				break;
			}
			else {
				Log.v(TAG, "weatherDTO for :" + day
						+ " is invalid ");
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
			Log.e(TAG, "sleep interrupted for day  " + day);
		}
	}
}
