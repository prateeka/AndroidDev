package com.google.android.examples.webmap;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebMapActivity extends Activity {
	
	private WebView webView;
	
	@Override
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setupWebView();
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
	}
	
	/** Sets up the WebView object and loads the URL of the page **/
	private void setupWebView() {
		final String centerURL = getCenterCoord();
		webView = (WebView) findViewById(R.id.webview);
		
		webView.getSettings().setJavaScriptEnabled(true);
		// Wait for the page to load then send the location information
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url)
			{
				webView.loadUrl(centerURL);
			}
		});
		webView.loadUrl("file:///android_asset/html/map.html");
		/** Allows JavaScript calls to access application resources **/
		webView.addJavascriptInterface(new JavaScriptInterface(), "android");
	}
	
	protected String getCenterCoord() {
		double longitude = -98.08646875;
		double latitude = 36.9331485912115;
		return "javascript:centerAt(" +
				latitude + "," + longitude + ")";
	}
	
	/**
	 * Sets up the interface for getting access to Latitude and Longitude data
	 * from device
	 **/
	private class JavaScriptInterface {
		double longitude = -98.08646875;
		double latitude = 36.9331485912115;
		
		public double getLatitude() {
			return latitude;
		}
		
		public double getLongitude() {
			return longitude;
		}
		
	}
	
}