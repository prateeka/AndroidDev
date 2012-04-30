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

function addLockButton(label) {
	var controlUI = document.createElement('div');
	controlUI.style.backgroundColor = 'white';
	controlUI.style.borderStyle = 'solid';
	controlUI.style.borderWidth = '2px';
	controlUI.style.cursor = 'pointer';
	controlUI.style.textAlign = 'center';
	controlUI.title = 'Click to set the map to Home';

	// Set CSS for the control interior.
	var controlText = document.createElement('div');
	controlText.style.fontFamily = 'Arial,sans-serif';
	controlText.style.fontSize = '12px';
	controlText.style.paddingLeft = '4px';
	controlText.style.paddingRight = '4px';
	controlText.innerHTML = label;
	controlUI.appendChild(controlText);
	
	return controlUI;
}

function addButtonListeners(lockButton, nextButton) {
	var dummyLatLng = gMarker.getPosition();
	var validSelection = notifyJava(dummyLatLng);
	lockButton.style.visibility = 'hidden';	
	nextButton.style.visibility = 'visible';   
}

function addCustomControls() 
{
	var controlDiv = document.createElement('div');
	
	// Set CSS styles for the DIV containing the control
	// Setting padding to 5 px will offset the control
	// from the edge of the map.
	controlDiv.style.padding = '5px';

	var lockButton = addLockButton('<strong>Locked<strong>');
	controlDiv.appendChild(lockButton);
	
	var nextButton = addLockButton('<strong>Next<strong>');
	controlDiv.appendChild(nextButton);
	nextButton.style.visibility = 'hidden';   
	
	// Setup the click event listeners
	google.maps.event.addDomListener(lockButton, 'click', function() {
		addButtonListeners(lockButton, nextButton);
	});
	
	controlDiv.index = 1;
	return controlDiv;
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

function getMap() {
	var myStyle = getStyleOptions(),
		myOptions = getMapOptions(getInitialLatLng(), getInitialZoom()),
		map = new google.maps.Map(document.getElementById("map_canvas"),
			myOptions);
	map.mapTypes.set('mystyle', new google.maps.StyledMapType(myStyle, {
		name : 'My Style'
	}));

	var customControlDiv = addCustomControls();
	map.controls[google.maps.ControlPosition.TOP_RIGHT].push(customControlDiv);
	  
	return map;
}

function initialize() {
	gMap = getMap();
	gMarker = getMarker();
	addMapListeners();
}