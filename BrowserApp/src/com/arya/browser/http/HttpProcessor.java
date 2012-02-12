package com.arya.browser.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

/*
 * This class accesses a URL using HTTPClient. Depending upon the request, it
 * retrieves the URL source or URL cookies and returns them to the requesting
 * component.
 */

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
		String pageSource = null;
		HttpEntity entity = null;
		try {
			HttpResponse response = getHttpResponse(url);
			if (isHttpResponseValid(url, response)) {
				entity = getHttpEntity(response);
				pageSource = processStream(entity);
			}
		}
		catch (IOException e) {
			Log.e(TAG, "IOException occured for url: " + url);
			throw new RuntimeException(e);
		}
		catch (IllegalStateException e) {
			Log.e(TAG, "IllegalStateException occured for url: " + url);
			throw new RuntimeException(e);
		}
		return pageSource;
	}
	
	public String getPageCookie(String url) {
		String cookie = null;
		try {
			HttpResponse response = getHttpResponse(url);
			if (isHttpResponseValid(url, response)) {
				cookie = retrieveCookies(url, response);
			}
		}
		catch (IOException e) {
			Log.e(TAG, "IOException occured for url: " + url);
			throw new RuntimeException(e);
		}
		catch (IllegalStateException e) {
			Log.e(TAG, "IllegalStateException occured for url: " + url);
			throw new RuntimeException(e);
		}
		return cookie;
	}
	
	private String retrieveCookies(String url, HttpResponse response) {
		StringBuilder cookieString = new StringBuilder();
		HeaderIterator it = response.headerIterator("Set-Cookie");
		while (it.hasNext()) {
			Header header = it.nextHeader();
			Log.d(TAG, "cookie for url: " + url + " is :" + header);
			cookieString.append(header).append("\n");
		}
		if (cookieString.length() == 0) {
			Log.i(TAG, "NO cookies found for url: " + url);
		}
		return cookieString.toString();
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
	
	private HttpEntity getHttpEntity(HttpResponse response) throws IOException {
		HttpEntity entity = null;
		entity = response.getEntity();
		return entity;
	}
	
	protected boolean isHttpResponseValid(String url, HttpResponse response) {
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if (statusCode == 200) {
			Log.i(
					TAG,
					"Successfully obtained response status=200 for URL: "
							+ url);
			return true;
		} else {
			Log.e(
					TAG,
					"Failed to get response status=200 for URL: " + url);
			return false;
		}
	}
	
	protected HttpResponse getHttpResponse(String url)
			throws IOException, ClientProtocolException {
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = client.execute(httpGet);
		return response;
	}
	
}
