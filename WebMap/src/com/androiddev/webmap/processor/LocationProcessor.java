package com.androiddev.webmap.processor;

import android.util.Log;

/*
 * This class represents acts as processor for any location. It includes:
 * a. Co-ordinates polygon for a location
 * b. Logic to determine if a co-ordinate passed is inside the location polygon
 * c. center co-ordinates for map when trying to locate this location
 * d. zoom level for map when trying to locate this location
 */
public class LocationProcessor {
	
	private static final String TAG = "LocationProcessor";
	
	final double centerLongitude;
	final double centerLatitude;
	final int zoom;
	
	public LocationProcessor(double centerLongitude, double centerLatitude,
			int zoom) {
		this.centerLongitude = centerLongitude;
		this.centerLatitude = centerLatitude;
		this.zoom = zoom;
	}
	
	public double getCenterLongitude() {
		return centerLongitude;
	}
	
	public double getCenterLatitude() {
		return centerLatitude;
	}
	
	public boolean isCorrectLocation(double lat, double lng) {
		Log.d(TAG, "clicked at " + lat + ":" + lng);
		return true;
	}
	
	public int getZoom() {
		return zoom;
	}
}
