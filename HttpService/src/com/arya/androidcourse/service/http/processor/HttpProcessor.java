package com.arya.androidcourse.service.http.processor;

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
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.arya.androidcourse.service.http.ParseableByteArray;

public class HttpProcessor {
	public final String TAG = "HttpProcessor";
	
	public String getTextContent(String url) {
		String feed = null;
		HttpEntity entity = null;
		try {
			entity = getHttpEntity(url);
			feed = processTextStream(entity);
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
	
	public ParseableByteArray getImageContent(String url) {
		Log.d(TAG, "getImageContent called for url: " + url);
		
		byte[] imageBytes = null;
		HttpEntity entity = null;
		try {
			entity = getHttpEntity(url);
			Log.d(TAG, "getImageContent successfully obtained entity for url: "
					+ url);
			imageBytes = processImageStream(entity);
			Log.d(
					TAG,
					"getImageContent successfully obtained imageBytes of length : "
							+ imageBytes.length + " for url: "
							+ url);
		}
		catch (IOException e) {
			Log.e(TAG, "IOException occured for url: " + url);
			throw new RuntimeException(e);
		}
		
		return new ParseableByteArray(imageBytes);
	}
	
	private byte[] processImageStream(HttpEntity entity) throws IOException {
		byte[] image = EntityUtils.toByteArray(entity);
		return image;
	}
	
	private String processTextStream(HttpEntity entity)
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
