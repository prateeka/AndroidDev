var map;

function initialize() {
	map = getMap();
	var marker = getMarker(map);
	addMapListeners(map, marker);
}

function getMap() {
	var myStyle = getStyleOptions();

	var myOptions = getMapOptions(getInitialLatLng());

	var map = new google.maps.Map(document.getElementById("map_canvas"),
			myOptions);
	map.mapTypes.set('mystyle', new google.maps.StyledMapType(myStyle, {
		name : 'My Style'
	}));

	return map;
}

function mapSingleClickListener(marker, latLng) {
	placeMarker(marker, latLng);
	centerAt(latLng);
	notifyJava(latLng);
}

function centerAt(latitude, longitude) {
	centerAt(getLatLng(latitude, longitude));
}

function centerAt(latLng) {
	map.panTo(latLng);
}

function placeMarker(marker, location) {
	marker.setPosition(location);
}

function getStyleOptions() {
	return [ {
		featureType : "administrative",
		elementType : "labels",
		stylers : [ {
			visibility : "off"
		} ]
	}, {
		featureType : "poi",
		elementType : "labels",
		stylers : [ {
			visibility : "off"
		} ]
	}, {
		featureType : "water",
		elementType : "labels",
		stylers : [ {
			visibility : "off"
		} ]
	}, {
		featureType : "road",
		elementType : "labels",
		stylers : [ {
			visibility : "off"
		} ]
	} ];
}

function getMapOptions(latLng) {
	return {
		zoom : 4,
		center : latLng,
		mapTypeControlOptions : {
			mapTypeIds : [ 'mystyle', google.maps.MapTypeId.ROADMAP,
					google.maps.MapTypeId.TERRAIN ]
		},
		mapTypeId : 'mystyle'
	};
}

function getLatLng(latitude, longitude) {
	return new google.maps.LatLng(latitude, longitude);
}

function getInitialLatLng() {
	var latitude = 0;
	var longitude = 0;
	if (window.android) {
		latitude = window.android.getLatitude();
		longitude = window.android.getLongitude();
	}
	return getLatLng(latitude, longitude);
}

function getMarker(map) {
	return new google.maps.Marker({
		position : map.getCenter(),
		map : map,
		title : 'Click to zoom'
	});
}

function addMapListeners(map, marker) {
	google.maps.event.addListener(map, 'click', function(event) {
		mapSingleClickListener(marker, event.latLng);
	});
}

function notifyJava(latLng) {
	window.android.clicked(latLng.lat(), latLng.lng());
}