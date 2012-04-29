package com.androiddev.webmap.processor;

import android.util.Log;

public class LocationProcessor {
	
	private static final String TAG = "LocationProcessor";
	
	final double centerLongitude;
	final double centerLatitude;
	
	public LocationProcessor(double centerLongitude, double centerLatitude) {
		this.centerLongitude = centerLongitude;
		this.centerLatitude = centerLatitude;
	}
	
	public double getCenterLongitude() {
		return centerLongitude;
	}
	
	public double getCenterLatitude() {
		return centerLatitude;
	}
	
	public boolean isCorrectLocation(double lat, double lng) {
		Log.d(TAG, "clicked at " + lat + ":" + lng);
		return false;
	}
}
