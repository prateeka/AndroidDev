package com.androiddev.webmap.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.androiddev.webmap.jsintf.JavaScriptInterface;
import com.androiddev.webmap.location.LocationBuilder;
import com.androiddev.webmap.location.LocationHolder;

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
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		
		WebViewClient webViewClient = new WebViewClient() {
		};
		
		webView.setWebViewClient(webViewClient);
		
		LocationHolder locHolder = getLocationHolder();
		JavaScriptInterface jsIntf = new JavaScriptInterface(
				webViewClient,
				locHolder);
		/** Allows JavaScript calls to access application resources **/
		webView.addJavascriptInterface(jsIntf, "android");
		
		webView.loadUrl("file:///android_asset/html/map.html");
	}
	
	private LocationHolder getLocationHolder() {
		LocationBuilder locBuilder = LocationBuilder.getInstance();
		LocationHolder locHolder = locBuilder.getLocationHolder();
		return locHolder;
	}
}