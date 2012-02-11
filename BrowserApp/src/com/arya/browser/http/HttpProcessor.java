package com.arya.browser.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class HttpProcessor {
	public final String TAG = "HttpProcessor";
	private static HttpProcessor thisInstance;
	
	private HttpProcessor() {
	}
	
	public static HttpProcessor getInstance() {
		if (thisInstance == null) {
			thisInstance = new HttpProcessor();
		}
		return thisInstance;
	}
	
	public String getPageSource(String url) {
		String feed = null;
		HttpEntity entity = null;
		try {
			entity = getHttpEntity(url);
			feed = processStream(entity);
		}
		catch (IOException e) {
			Log.e(TAG, "IOException occured for url: " + url);
			throw new RuntimeException(e);
		}
		catch (IllegalStateException e) {
			Log.e(TAG, "IllegalStateException occured for url: " + url);
			throw new RuntimeException(e);
		}
		return feed;
	}
	
	private String processStream(HttpEntity entity)
			throws IllegalStateException,
			IOException {
		StringBuilder builder = new StringBuilder();
		
		InputStream content = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				content));
		String line;
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}
		return builder.toString();
	}
	
	private HttpEntity getHttpEntity(String url) throws IOException {
		HttpEntity entity = null;
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = client.execute(httpGet);
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if (statusCode == 200) {
			Log.i(
					TAG,
					"Successfully obtained response status=200 for URL: "
							+ url);
			entity = response.getEntity();
		} else {
			Log.e(
					TAG,
					"Failed to get response status=200 for URL: " + url);
		}
		return entity;
	}
}