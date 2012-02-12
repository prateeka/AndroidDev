package com.arya.androidcourse.service.http;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.arya.androidcourse.service.http.processor.HttpProcessor;
import com.arya.androidcourse.service.http.processor.HttpProcessorCache;

public class HttpService extends Service {
	private final String TAG = "HttpService";
	
	private final IHttpService.Stub mBinder = new IHttpService.Stub() {
		HttpProcessor httpProcessor = getHttpProcessor();
		
		@Override
		public String getTextContent(String url) throws RemoteException {
			Log.d(TAG, "entering into getTextContent for url " + url);
			return httpProcessor.getTextContent(url);
		}
		
		@Override
		public ParseableByteArray getImageContent(String url)
				throws RemoteException {
			Log.d(TAG, "entering into getImageContent for url " + url);
			return httpProcessor.getImageContent(url);
		}
		
		private HttpProcessor getHttpProcessor() {
			return HttpProcessorCache.getInstance();
		}
		
	};
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
}
