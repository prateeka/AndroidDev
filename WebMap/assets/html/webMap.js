"use strict";

var gMap;
var gMarker;

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

function getMapOptions(latLng, zoomVal) {
	return {
		zoom : zoomVal,
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

function getInitialZoom() {
	var zoomVal = window.android.getZoom();
	return zoomVal;
}

function centerAt(latitude, longitude) {
	centerAt(getLatLng(latitude, longitude));
}

function centerAt(latLng) {
	gMap.panTo(latLng);
}

function placeMarker(location) {
	gMarker.setPosition(location);
}

function notifyJava(latLng) {
	window.android.clicked(latLng.lat(), latLng.lng());
}

function mapSingleClickListener(latLng) {
	placeMarker(latLng);
	centerAt(latLng);
}

/**
 * The HomeControl adds a control to the map that simply
 * returns the user to Chicago. This constructor takes
 * the control DIV as an argument.
 */

function HomeControl(controlDiv) {

	// Set CSS styles for the DIV containing the control
	// Setting padding to 5 px will offset the control
	// from the edge of the map.
	controlDiv.style.padding = '5px';

	// Set CSS for the control border.
	var controlUI = document.createElement('div');
	controlUI.style.backgroundColor = 'white';
	controlUI.style.borderStyle = 'solid';
	controlUI.style.borderWidth = '2px';
	controlUI.style.cursor = 'pointer';
	controlUI.style.textAlign = 'center';
	controlUI.title = 'Click to set the map to Home';
	controlDiv.appendChild(controlUI);

	// Set CSS for the control interior.
	var controlText = document.createElement('div');
	controlText.style.fontFamily = 'Arial,sans-serif';
	controlText.style.fontSize = '12px';
	controlText.style.paddingLeft = '4px';
	controlText.style.paddingRight = '4px';
	controlText.innerHTML = '<strong>Locked<strong>';
	controlUI.appendChild(controlText);
	
	// Setup the click event listeners
	google.maps.event.addDomListener(controlUI, 'click', function() {
		addButtonListeners();
	});
}

function addButtonListeners() {
	var dummyLatLng = gMarker.getPosition();
	notifyJava(dummyLatLng);
}

function addMapListeners() {
	google.maps.event.addListener(gMap, 'click', function (event) {
		mapSingleClickListener(event.latLng);
	});
}


function getMarker() {
	return new google.maps.Marker({
		position : gMap.getCenter(),
		map : gMap,
		title : 'Click to zoom'
	});
}

function addLockPositionButton() 
{
	var homeControlDiv = document.createElement('div');
	var homeControl = new HomeControl(homeControlDiv, gMap);
		
	homeControlDiv.index = 1;
	return homeControlDiv;
}

function getMap() {
	var myStyle = getStyleOptions(),
		myOptions = getMapOptions(getInitialLatLng(), getInitialZoom()),
		map = new google.maps.Map(document.getElementById("map_canvas"),
			myOptions);
	map.mapTypes.set('mystyle', new google.maps.StyledMapType(myStyle, {
		name : 'My Style'
	}));

	var homeControlDiv = addLockPositionButton();
	map.controls[google.maps.ControlPosition.TOP_RIGHT].push(homeControlDiv);
	  
	return map;
}

function initialize() {
	gMap = getMap();
	gMarker = getMarker();
	addMapListeners();
}