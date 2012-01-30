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
	
	private static final String DownLoadingData = "Downloading Data";
	private final IHttpService httpService;
	private WeatherDTO weatherDTO;
	private TextView conditionsText;
	private TextView celsiusTempText;
	private TextView farenTempText;
	private TextView errorMsg;
	private final WeatherDataProvider weatherDataProvider;
	
	DownloadWeatherTask(Context context, IHttpService httpService) {
		initViews(context);
		this.httpService = httpService;
		weatherDataProvider = new WeatherDataProvider(httpService);
	}
	
	// Called from main thread to re-attach
	protected void setContext(Context context) {
		Log.v(TAG, "setContext called");
		initViews(context);
		if (getStatus() == AsyncTask.Status.FINISHED) {
			displayWeatherData();
		} else {
			displayDownloadingDataMsg();
		}
	}
	
	protected void initViews(Context context) {
		conditionsText = (TextView)
				((Activity) context).findViewById(R.id.conditions);
		celsiusTempText = (TextView)
				((Activity) context).findViewById(R.id.celsiusTemp);
		farenTempText = (TextView)
				((Activity) context).findViewById(R.id.farenTemp);
		errorMsg = (TextView)
				((Activity) context).findViewById(R.id.errorMsg);
	}
	
	@Override
	protected void onPreExecute() {
		displayDownloadingDataMsg();
	}
	
	protected void displayDownloadingDataMsg() {
		setTextMsg(conditionsText, DownLoadingData);
		setTextMsg(celsiusTempText, DownLoadingData);
		setTextMsg(farenTempText, DownLoadingData);
	}
	
	@Override
	protected WeatherDTO doInBackground(String... urls) {
		Log.v(TAG, "initiating downloading weather data");
		return downloadWeatherData(WeatherDays.TODAY);
		/*-try {
		 return parseFeed(urls[0]);
		 }
		 catch (JSONException e) {
		 Log.e(TAG, "JSONException thrown " + e);
		 }
		 catch (RemoteException e) {
		 Log.e(TAG, "RemoteException thrown " + e);
		 }
		 return null;*/
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress) {
		conditionsText.setText("Progress so far: " + progress[0]);
	}
	
	@Override
	protected void onPostExecute(WeatherDTO result) {
		if (result != null) {
			weatherDTO = result;
			displayWeatherData();
		}
		else {
			errorMsg
					.setText("Problem downloading weather data. Please try later.");
		}
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
	
	private WeatherDTO downloadWeatherData(WeatherDays day) {
		WeatherDTO weatherDTO = null;
		
		synchronized (day) {
			while ((weatherDTO == null) || !weatherDTO.isValid()) {
				weatherDTO = weatherDataProvider.getWeather(day);
				
				if (weatherDTO != null) {
					if (weatherDTO.isValid()) {
						Log.d(TAG, "weatherDTO for :" + day + " is "
								+ weatherDTO);
					} else {
						Log.v(TAG, "weatherDTO for :" + day
								+ " is invalid ");
					}
				}
			}
		}
		return weatherDTO;
	}
}

/*-private Integer parseFeed(String url) throws JSONException, RemoteException {
 String feed = httpService.getFeed(url);
 JSONArray jsonArray = new JSONArray(feed);
 Log.i(TAG,
 "Number of entries " + jsonArray.length());
 for (int i = 0; i < jsonArray.length(); i++) {
 JSONObject jsonObject = jsonArray.getJSONObject(i);
 Log.i(TAG, jsonObject.getString("text"));
 }
 return feed.length();
 }*/