package com.androidcourse.client.weather;

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
	private final IHttpService httpService;
	private final String TAG = "DownloadWeatherTask";
	private Integer downloadedResponseLength;
	private TextView mText;
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
			setTextMsg("In Progress");
		}
	}
	
	protected void initViews(Context context) {
		mText = (TextView)
				((Activity) context).findViewById(R.id.textMsg);
		errorMsg = (TextView)
				((Activity) context).findViewById(R.id.errorMsg);
	}
	
	@Override
	protected void onPreExecute() {
		setTextMsg("In Progress");
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
		mText.setText("Progress so far: " + progress[0]);
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
		mText.setText(msg);
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
