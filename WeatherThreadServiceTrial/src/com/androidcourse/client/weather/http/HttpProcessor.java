package com.androidcourse.client.weather.http;

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

public class HttpProcessor {
	public final String TAG = "HttpProcessor";
	
	public String getFeed(String url) {
		String feed = null;
		HttpEntity entity = null;
		try {
			entity = getHttpEntity(url);
			feed = getFeed(entity);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		catch (IllegalStateException e) {
			throw new RuntimeException(e);
		}
		return feed;
	}
	
	private String getFeed(HttpEntity entity) throws IllegalStateException,
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
			System.out
					.println("Successfully obtained response status=200 for URL: "
							+ url);
			entity = response.getEntity();
		} else {
			System.out.println("Failed to get response status=200 for URL: "
					+ url);
		}
		
		return entity;
	}
}
