package com.androiddev.webmap.location;

import java.util.ArrayList;
import java.util.List;

class LocationDAO {
	private static LocationDAO thisInstance;
	
	private LocationDAO() {
	}
	
	static LocationDAO getInstance() {
		if (thisInstance == null) {
			thisInstance = new LocationDAO();
		}
		return thisInstance;
	}
	
	List<LocationVO> getLocations() {
		List<LocationVO> locations = new ArrayList<LocationVO>();
		locations.add(buildLocationVO(
				"Kansas",
				-98.08646875,
				36.9331485912115,
				3));
		return locations;
	}
	
	private LocationVO
			buildLocationVO(String name, double centerLongitude,
					double centerLatitude, int zoom) {
		return new LocationVO(name, centerLongitude, centerLatitude, zoom);
	}
}
