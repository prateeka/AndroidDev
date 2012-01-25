package com.androidcourse.client.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.androidcourse.weather.R;
import com.arya.androidcourse.service.http.IHttpService;

public class WeatherActivity extends Activity {
	
	private final String TAG = "WeatherActivity";
	private final String URL = "http://twitter.com/statuses/user_timeline/vogella.json";
	private IHttpService httpService = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		bindService();
	}
	
	private void bindService() {
		Log.i(TAG, "calling bindService");
		bindService(new Intent(IHttpService.class
				.getName()),
				serConn, Context.BIND_AUTO_CREATE);
		Log.i(TAG, "bind service returning");
	}
	
	private void logFeed() {
		try {
			String feed = httpService.getFeed(URL);
			JSONArray jsonArray = new JSONArray(feed);
			Log.i(TAG,
					"Number of entries " + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Log.i(TAG, jsonObject.getString("text"));
			}
		}
		catch (JSONException e) {
			Log.e(TAG, "JSONException thrown " + e);
		}
		catch (RemoteException e) {
			Log.e(TAG, "RemoteException thrown " + e);
		}
	}
	
	private final ServiceConnection serConn = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service)
		{
			Log.v(TAG, "onServiceConnected() called");
			httpService = IHttpService.Stub.asInterface(service);
			logFeed();
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.v(TAG, "onServiceDisconnected() called");
			httpService = null;
		}
	};
}