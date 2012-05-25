package com.androiddev.webmap.location;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

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
	
	final String JSON_NAME = "NAME";
	final String JSON_CENTER_LONGITUDE = "CENTER_LONGITUDE";
	final String JSON_CENTER_LATITUDE = "CENTER_LATITUDE";
	final String JSON_ZOOM = "ZOOM";
	final String JSON_BOUNDARY_X = "BOUNDARY_X";
	final String JSON_BOUNDARY_Y = "BOUNDARY_Y";
	
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
	
	@Override
	public String toString() {
		Map<String, String> attrMap = getAttrMap();
		JSONObject jsonObj = new JSONObject(attrMap);
		return jsonObj.toString();
	}
	
	private Map<String, String> getAttrMap() {
		Map<String, String> attrMap = new HashMap<String, String>();
		attrMap.put(JSON_NAME, getName());
		attrMap.put(JSON_CENTER_LONGITUDE, getCenterLongitude().toString());
		attrMap.put(JSON_CENTER_LATITUDE, getCenterLatitude().toString());
		attrMap.put(JSON_ZOOM, getZoom().toString());
		attrMap.put(
				JSON_BOUNDARY_X,
				new JSONArray(Arrays.asList(getBoundaryXPoints())).toString());
		attrMap.put(
				JSON_BOUNDARY_Y,
				new JSONArray(Arrays.asList(getBoundaryYPoints())).toString());
		return attrMap;
	}
}
