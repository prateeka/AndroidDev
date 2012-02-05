package com.arya.androidcourse.service.http;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class HttpService extends Service {
	public final String TAG = "HttpService";
	
	private final IHttpService.Stub mBinder = new IHttpService.Stub() {
		HttpProcessor httpProcessor = getHttpProcessor();
		
		@Override
		public String getTextContent(String url) throws RemoteException {
			return httpProcessor.getFeed(url);
		}
		
		@Override
		public String getImageContent(String url) throws RemoteException {
			return null;
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
