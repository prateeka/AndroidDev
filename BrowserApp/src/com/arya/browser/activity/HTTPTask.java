package com.arya.browser.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.arya.browser.http.HttpProcessor;

public class HTTPTask extends AsyncTask<String, Void, String> {
	private static final String TAG = "DownloadWeatherTask";
	private TextView displayTextView;
	private final String downloadSource;
	
	HTTPTask(Activity activity) {
		initViews(activity);
		downloadSource = activity.getResources().getString(
				R.string.displaySource);
	}
	
	protected void initViews(Activity activity) {
		displayTextView = (TextView) (activity.findViewById(R.id.textView1));
	}
	
	@Override
	protected void onPreExecute() {
		displayDownloadingMsg();
	}
	
	@Override
	protected String doInBackground(String... str) {
		String action = str[0];
		String url = str[1];
		Log.d(TAG, "initiating downloading data for url  " + url);
		if (action.equals(downloadSource)) {
			return downloadPageSource(url);
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(String stringToDisplay) {
		if (stringToDisplay != null) {
			displayResult(stringToDisplay);
		}
		else {
			displayErrorMsg();
		}
	}
	
	private void displayErrorMsg() {
		final String ERROR_MSG = "Problem downloading data. Please try later";
		setTextMsg(displayTextView, ERROR_MSG);
	}
	
	protected void displayDownloadingMsg() {
		final String DOWNLOADING_DATA = "Downloading Data";
		setTextMsg(displayTextView, DOWNLOADING_DATA);
	}
	
	protected void displayResult(String stringToDisplay) {
		setTextMsg(displayTextView, stringToDisplay);
	}
	
	protected void setTextMsg(TextView view, String msg) {
		// Log.d(TAG, "view:msg is " + view + ":" + msg);
		view.setText(msg);
	}
	
	private String downloadPageSource(String url) {
		HttpProcessor httpProcessor = HttpProcessor.getInstance();
		String strToDisplay = httpProcessor.getPageSource(url);
		return strToDisplay;
	}
	
}
