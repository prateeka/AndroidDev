package com.androidcourse.client.weather.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;

import com.arya.androidcourse.service.http.IHttpService;

public class DownloadWeatherTask extends AsyncTask<String, Integer, Integer> {
	private static final String DownLoadingData = "Downloading Data";
	private final IHttpService httpService;
	private final String TAG = "DownloadWeatherTask";
	private Integer downloadedResponseLength;
	private TextView conditionsText;
	private TextView celsiusTempText;
	private TextView farenTempText;
	private TextView errorMsg;
	
	DownloadWeatherTask(Context context, IHttpService httpService) {
		initViews(context);
		this.httpService = httpService;
	}
	
	// Called from main thread to re-attach
	protected void setContext(Context context) {
		initViews(context);
		if (getStatus() == AsyncTask.Status.FINISHED) {
			displayWeatherData();
		} else {
			setTextMsg(DownLoadingData);
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
		setTextMsg(DownLoadingData);
	}
	
	@Override
	protected Integer doInBackground(String... urls) {
		Log.v("doInBackground", "doing download of image...");
		try {
			return parseFeed(urls[0]);
		}
		catch (JSONException e) {
			Log.e(TAG, "JSONException thrown " + e);
		}
		catch (RemoteException e) {
			Log.e(TAG, "RemoteException thrown " + e);
		}
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress) {
		conditionsText.setText("Progress so far: " + progress[0]);
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		if (result != null) {
			downloadedResponseLength = result;
			displayWeatherData();
		}
		else {
			errorMsg
					.setText("Problem downloading weather data. Please try later.");
		}
	}
	
	protected void displayWeatherData() {
		if (downloadedResponseLength != null) {
			Log.i(TAG, "downloadedResponseLength is "
					+ downloadedResponseLength);
			setTextMsg(downloadedResponseLength.toString());
		}
	}
	
	protected void setTextMsg(String msg) {
		conditionsText.setText(msg);
		celsiusTempText.setText(msg);
		farenTempText.setText(msg);
	}
	
	private Integer parseFeed(String url) throws JSONException, RemoteException {
		String feed = httpService.getFeed(url);
		JSONArray jsonArray = new JSONArray(feed);
		Log.i(TAG,
				"Number of entries " + jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			Log.i(TAG, jsonObject.getString("text"));
		}
		return feed.length();
	}
}
