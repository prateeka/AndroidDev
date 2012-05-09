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
				3,
				new Double[] { -109.05082421875, -109.05082421875,
						-102.01957421875, -102.10746484375 },
				new Double[] { 40.99725687752573, 37.00337044713457,
						37.0384570771806, 40.96408120506293 }));
		return locations;
	}
	
	private LocationVO
			buildLocationVO(String name, Double centerLongitude,
					Double centerLatitude, Integer zoom,
					Double[] xPoints, Double[] yPoints) {
		return new LocationVO(
				name,
				centerLongitude,
				centerLatitude,
				zoom,
				xPoints,
				yPoints);
	}
}
