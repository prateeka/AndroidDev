package com.androiddev.webmap.jsintf;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;

import android.util.Log;
import android.webkit.WebViewClient;

import com.androiddev.webmap.location.LocationHolder;

public class JavaScriptInterface {
	private static final String TAG = "JavaScriptInterface ";
	
	/**
	 * This class helps for to and for communication with Java script;
	 * It provides initial latitude and longitude to Javascript for initial
	 * center co-ordinates. It also receives the user chosen location
	 * co-ordinates from Javascript to confirm if the click is in valid
	 * location.
	 **/
	final WebViewClient webViewClient;
	
	final LocationHolder locHolder;
	
	public JavaScriptInterface(WebViewClient webViewClient,
			LocationHolder locHolder) {
		this.webViewClient = webViewClient;
		this.locHolder = locHolder;
	}
	
	public void clickedAt(double lat, double lng) {
		Log.d(TAG, "clicked At " + lat + " : " + lng);
		// return locHolder.isCorrectLocation(lat, lng);
	}
	
	public double getCenterLatitude(int idx) {
		Log.d(TAG, "getCenterLatitude is " + locHolder.getCenterLatitude(idx));
		return locHolder.getCenterLatitude(idx);
	}
	
	public double getCenterLongitude(int idx) {
		Log.d(TAG, "getCenterLongitude is "
				+ locHolder.getCenterLongitude(idx));
		return locHolder.getCenterLongitude(idx);
	}
	
	public int getZoom(int idx) {
		Log.d(TAG, "getZoom is " + locHolder.getZoom(idx));
		Log.d(TAG, "toString Loc object is :" + locHolder.getAllLocations());
		return locHolder.getZoom(idx);
	}
	
	public String getXPoints(int idx) {
		Double points[] = locHolder.getXPoints(idx);
		
		// conversion to JSON as unable to pass array through javascript bridge
		List<Double> pointsList = Arrays.asList(points);
		JSONArray jsArray = new JSONArray(pointsList);
		Log.d(TAG, "JSONArray xPoints being returned : " + jsArray);
		
		return jsArray.toString();
	}
	
	public String getYPoints(int idx) {
		Double points[] = locHolder.getYPoints(idx);
		
		// conversion to JSON as unable to pass array through javascript bridge
		List<Double> pointsList = Arrays.asList(points);
		JSONArray jsArray = new JSONArray(pointsList);
		Log.d(TAG, "JSONArray yPoints being returned : " + jsArray);
		
		return jsArray.toString();
	}
	
}
