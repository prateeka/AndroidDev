package com.androiddev.webmap.jsintf;

import android.webkit.WebViewClient;

import com.androiddev.webmap.location.LocationProcessor;

public class JavaScriptInterface {
	/**
	 * This class helps for to and for communication with Java script;
	 * It provides initial latitude and longitude to Javascript for initial
	 * center co-ordinates. It also receives the user chosen location
	 * co-ordinates from Javascript.
	 **/
	final WebViewClient webViewClient;
	
	final LocationProcessor locProc;
	
	public JavaScriptInterface(WebViewClient webViewClient,
			LocationProcessor locProc) {
		this.webViewClient = webViewClient;
		this.locProc = locProc;
	}
	
	public boolean clickedAt(double lat, double lng) {
		return locProc.isCorrectLocation(lat, lng);
	}
	
	public double getCenterLatitude(int idx) {
		return locProc.getCenterLatitude();
	}
	
	public double getCenterLongitude(int idx) {
		return locProc.getCenterLongitude();
	}
	
	public int getZoom(int idx) {
		return locProc.getZoom();
	}
}
