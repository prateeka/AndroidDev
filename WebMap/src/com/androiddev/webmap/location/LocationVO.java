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
	final Double centerLongitude;
	final Double centerLatitude;
	final Integer zoom;
	final Double[] boundaryXPoints;
	final Double[] boundaryYPoints;
	
	LocationVO(String name, Double centerLongitude,
			Double centerLatitude, Integer zoom,
			Double[] boundaryXPoints, Double[] boundaryYPoints) {
		this.name = name;
		this.centerLongitude = centerLongitude;
		this.centerLatitude = centerLatitude;
		this.zoom = zoom;
		this.boundaryXPoints = boundaryXPoints;
		this.boundaryYPoints = boundaryYPoints;
	}
	
	public Double[] getBoundaryXPoints() {
		return boundaryXPoints;
	}
	
	public Double[] getBoundaryYPoints() {
		return boundaryYPoints;
	}
	
	Double getCenterLongitude() {
		return centerLongitude;
	}
	
	Double getCenterLatitude() {
		return centerLatitude;
	}
	
	boolean isCorrectLocation(Double lat, Double lng) {
		Log.d(TAG, "clicked at " + lat + ":" + lng);
		return true;
	}
	
	Integer getZoom() {
		return zoom;
	}
	
	String getName() {
		return name;
	}
}
