package com.androiddev.webmap.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.androiddev.webmap.jsintf.JavaScriptInterface;
import com.androiddev.webmap.location.LocationBuilder;
import com.androiddev.webmap.location.LocationHolder;

public class WebMapActivity extends Activity {
	
	private static final String TAG = "WebMapActivity";
	
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
		
		webView.setWebViewClient(new WebViewClient() {
		});
		
		webView.setWebChromeClient(getWebChromeClient());
		LocationHolder locHolder = getLocationHolder();
		JavaScriptInterface jsIntf = new JavaScriptInterface(
				new WebViewClient() {
				},
				locHolder);
		/** Allows JavaScript calls to access application resources **/
		webView.addJavascriptInterface(jsIntf, "android");
		
		webView.loadUrl("file:///android_asset/html/map.html");
	}
	
	protected WebChromeClient getWebChromeClient() {
		return new WebChromeClient() {
			@Override
			public boolean onConsoleMessage(ConsoleMessage cm) {
				Log.d(TAG, cm.message() + " -- From line "
						+ cm.lineNumber() + " of "
						+ cm.sourceId());
				return true;
			}
		};
	}
	
	private LocationHolder getLocationHolder() {
		LocationBuilder locBuilder = LocationBuilder.getInstance();
		LocationHolder locHolder = locBuilder.getLocationHolder();
		return locHolder;
	}
}