package com.google.android.examples.webmap;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebMapActivity extends Activity {
	
	private static final String MAP_URL = "http://gmaps-samples.googlecode.com/svn/trunk/articles-android-webmap/simple-android-map.html";
	private WebView webView;
	
	// private Location mostRecentLocation;;
	
	@Override
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		getLocation();
		setupWebView();
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
	}
	
	/** Sets up the WebView object and loads the URL of the page **/
	private void setupWebView() {
		/*-	final String centerURL = "javascript:centerAt(" +
					45.80271303708887 + "," +
					-122.11906352343749 + ")";*/
		webView = (WebView) findViewById(R.id.webview);
		
		final String centerURL = "javascript:centerAt12( "
				+
				"var latitude = 45.80271303708887; var longitude = -122.11906352343749;"
				+
				"var myLatlng = new google.maps.LatLng(latitude,longitude);"
				+
				"var myOptions = {zoom: 8,center: myLatlng,mapTypeId: google.maps.MapTypeId.ROADMAP}"
				+
				"map = new google.maps.Map(document.getElementById(\"map_canvas\"), myOptions);}";
		
		webView.getSettings().setJavaScriptEnabled(true);
		// Wait for the page to load then send the location information
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url)
			{
				webView.loadUrl(getWebpageHTML());
			}
		});
		webView.loadUrl(MAP_URL);
		
	}
	
	private String getWebpageHTML() {
		return null;
	}
	
	/**
	 * The Location Manager manages location providers. This code searches
	 * for the best provider of data (GPS, WiFi/cell phone tower lookup,
	 * some other mechanism) and finds the last known location.
	 **/
	private void getLocation() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String provider = locationManager.getBestProvider(criteria, true);
		
		// In order to make sure the device is getting location, request
		// updates. locationManager.requestLocationUpdates(provider, 1, 0,
		// this);
		/*-		mostRecentLocation = locationManager.getLastKnownLocation(provider);
		 System.out.println("mostRecentLocation is " + mostRecentLocation);
		 */}
	
}