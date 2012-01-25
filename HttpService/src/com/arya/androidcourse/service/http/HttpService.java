package com.arya.androidcourse.service.http;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class HttpService extends Service {
	public static final String TAG = "HttpService";
	
	private final IHttpService.Stub mBinder = new IHttpService.Stub() {
		HttpProcessor httpProcessor = getHttpProcessor();
		
		@Override
		public String getFeed(String url) throws RemoteException {
			return httpProcessor.getFeed(url);
		}
		
		private HttpProcessor getHttpProcessor() {
			return new HttpProcessor();
		}
	};
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
}
