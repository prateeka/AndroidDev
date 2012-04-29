"use strict";

var map;

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

function getLatLng(latitude, longitude) {
	return new google.maps.LatLng(latitude, longitude);
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

function getInitialLatLng() {
	var latitude = window.android.getCenterLatitude(),
		longitude = window.android.getCenterLongitude();
	return getLatLng(latitude, longitude);
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

function notifyJava(latLng) {
	window.android.clicked(latLng.lat(), latLng.lng());
}

function mapSingleClickListener(marker, latLng) {
	placeMarker(marker, latLng);
	centerAt(latLng);
	notifyJava(latLng);
}

function addMapListeners(map, marker) {
	google.maps.event.addListener(map, 'click', function (event) {
		mapSingleClickListener(marker, event.latLng);
	});
}


function getMarker(map) {
	return new google.maps.Marker({
		position : map.getCenter(),
		map : map,
		title : 'Click to zoom'
	});
}

function getMap() {
	var myStyle = getStyleOptions(),
		myOptions = getMapOptions(getInitialLatLng()),
		map = new google.maps.Map(document.getElementById("map_canvas"),
			myOptions);
	map.mapTypes.set('mystyle', new google.maps.StyledMapType(myStyle, {
		name : 'My Style'
	}));

	return map;
}

function initialize() {
	map = getMap();
	var marker = getMarker(map);
	addMapListeners(map, marker);
}