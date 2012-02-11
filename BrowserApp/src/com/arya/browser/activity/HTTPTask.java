package com.arya.browser.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.arya.browser.http.HttpProcessor;

public class HTTPTask extends AsyncTask<String, Void, String> {
	private static final String TAG = "DownloadWeatherTask";
	private TextView displayTextView;
	private final Activity activity;
	
	HTTPTask(Activity activity) {
		initViews(activity);
		this.activity = activity;
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
		String displaySource = activity.getResources().getString(
				R.string.displaySource);
		String displayCookie = activity.getResources().getString(
				R.string.displayCookie);
		
		String action = str[0];
		String url = str[1];
		
		Log.d(TAG, "initiating downloading data for url  " + url);
		if (action.equals(displaySource)) {
			return downloadPageSource(url);
		} else if (action.equals(displayCookie)) {
			return downloadPageCookies(url);
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
		HttpProcessor httpProcessor = getHttpProcessor();
		String strToDisplay = httpProcessor.getPageSource(url);
		return strToDisplay;
	}
	
	private String downloadPageCookies(String url) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected HttpProcessor getHttpProcessor() {
		return HttpProcessor.getInstance();
	}
	
}
