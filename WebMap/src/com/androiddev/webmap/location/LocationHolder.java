/*
 * This is a collection of LocationVO objects. This returns the attributes for
 * a location object requested.
 */
package com.androiddev.webmap.location;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

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
	
	public Double getCenterLatitude(int idx) {
		Double centerLatitude = locationsList.get(idx).getCenterLatitude();
		return centerLatitude;
	}
	
	public Double getCenterLongitude(int idx) {
		Double centerLongitude = locationsList.get(idx).getCenterLongitude();
		return centerLongitude;
	}
	
	public Integer getZoom(int idx) {
		int zoom = locationsList.get(idx).getZoom();
		return zoom;
	}
	
	/*-	public boolean isCorrectLocation(Double lat, Double lng) {
	 return false;
	 }
	 */
	
	public Double[] getXPoints(int idx) {
		return locationsList.get(idx).getBoundaryXPoints();
	}
	
	public Double[] getYPoints(int idx) {
		return locationsList.get(idx).getBoundaryYPoints();
	}
	
	public String getAllLocations() {
		/*-StringBuilder sb = new StringBuilder();
		for (LocationVO locVO : locationsList) {
			sb.append(locVO);
		}*/
		JSONArray jsArray = new JSONArray(locationsList);
		return jsArray.toString();
	}
}
