package com.androiddev.webmap.location;

import android.util.Log;

/*
 * This represents any location. It includes:
 * a. location name
 * b. edge co-ordinates defining a polygon for this location
 * c. center co-ordinates for map
 * d. zoom level for map
 */
class LocationVO {
	
	private static final String TAG = "LocationVO";
	
	final String name;
	final double centerLongitude;
	final double centerLatitude;
	final int zoom;
	
	LocationVO(String name, double centerLongitude,
			double centerLatitude,
			int zoom) {
		this.name = name;
		this.centerLongitude = centerLongitude;
		this.centerLatitude = centerLatitude;
		this.zoom = zoom;
	}
	
	double getCenterLongitude() {
		return centerLongitude;
	}
	
	double getCenterLatitude() {
		return centerLatitude;
	}
	
	boolean isCorrectLocation(double lat, double lng) {
		Log.d(TAG, "clicked at " + lat + ":" + lng);
		return true;
	}
	
	int getZoom() {
		return zoom;
	}
	
	String getName() {
		return name;
	}
}
