package com.androiddev.webmap.location;

import java.util.List;


/**
 * Generates LocationHolder which is a collection of LocationVO objects.
 */

public class LocationBuilder {
	private static LocationBuilder thisInstance;
	
	private LocationBuilder() {
	}
	
	public static LocationBuilder getInstance() {
		if (thisInstance == null) {
			thisInstance = new LocationBuilder();
		}
		return thisInstance;
	}
	
	public LocationHolder getLocationHolder() {
		LocationHolder locHolder = buildLocationHolder();
		return locHolder;
	}
	
	private LocationHolder buildLocationHolder() {
		LocationHolder locHolder = LocationHolder.getInstance();
		List<LocationVO> locations = getLocations();
		for (LocationVO location : locations) {
			locHolder.addLocation(location);
		}
		return locHolder;
	}
	
	private List<LocationVO> getLocations() {
		LocationDAO locDAO = LocationDAO.getInstance();
		List<LocationVO> locations = locDAO.getLocations();
		return locations;
	}
}
