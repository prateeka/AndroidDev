package com.androiddev.webmap.location;

import android.util.Log;

/*
 * This represents any location. It includes:
 * a. location name
 * b. edge co-ordinates defining a polygon for this location
 * c. center co-ordinates for map
 * d. zoom level for map
 */
public class Location {
	
	private static final String TAG = "Location";
	
	final String name;
	final double centerLongitude;
	final double centerLatitude;
	final int zoom;
	
	public Location(String name, double centerLongitude, double centerLatitude,
			int zoom) {
		this.name = name;
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
	
	public String getName() {
		return name;
	}
}
