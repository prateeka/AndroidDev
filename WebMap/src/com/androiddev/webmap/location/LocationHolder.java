/*
 * This is a collection of LocationVO objects. This returns the attributes for
 * a location object requested.
 */
package com.androiddev.webmap.location;

import java.util.ArrayList;
import java.util.List;

/**
 * @author asthanap
 */
public class LocationHolder {
	private static LocationHolder thisInstance;
	private final List<LocationVO> locationsList;
	
	private LocationHolder() {
		locationsList = new ArrayList<LocationVO>();
	}
	
	public static LocationHolder getInstance() {
		if (thisInstance == null) {
			thisInstance = new LocationHolder();
		}
		return thisInstance;
	}
	
	public void addLocation(LocationVO location) {
		locationsList.add(location);
	}
	
	public double getCenterLatitude(int idx) {
		double centerLatitude = locationsList.get(idx).getCenterLatitude();
		return centerLatitude;
	}
	
	public double getCenterLongitude(int idx) {
		double centerLongitude = locationsList.get(idx).getCenterLongitude();
		return centerLongitude;
	}
	
	public int getZoom(int idx) {
		int zoom = locationsList.get(idx).getZoom();
		return zoom;
	}
	
	public boolean isCorrectLocation(double lat, double lng) {
		return false;
	}
	
	public double[] getXPoints(int idx) {
		return locationsList.get(idx).getBoundaryXPoints();
	}
	
	public double[] getYPoints(int idx) {
		return locationsList.get(idx).getBoundaryYPoints();
	}
}
