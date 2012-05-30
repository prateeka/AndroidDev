"use strict";

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

function centerAt(latitude, longitude) {
	centerAt(getLatLng(latitude, longitude));
}

function centerAt(latLng) {
	gMap.panTo(latLng);
}

function placeMarker(location) {
	gMarker.setPosition(location);
}

function addControl(label) {
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

function addCustomControls() {
	var controlDiv = document.createElement('div');
	
	// Set CSS styles for the DIV containing the control
	// Setting padding to 5 px will offset the control
	// from the edge of the map.
	controlDiv.style.padding = '5px';

	var lockButton = addControl('<strong>Locked<strong>');
	controlDiv.appendChild(lockButton);
	
	// Setup the click event listeners
	google.maps.event.addDomListener(lockButton, 'click', function() {
		customControlClickListener(controlDiv, lockButton);
	});
	
	controlDiv.index = 1;
	return controlDiv;
}

function addMapListeners(map) {
	google.maps.event.addListener(map, 'click', function (event) {
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
		myOptions = getMapOptions(getLatLngForIdx(getIdx()), getZoomForIdx(getIdx())),
		map = new google.maps.Map(document.getElementById("map_canvas"),
			myOptions);
	map.mapTypes.set('mystyle', new google.maps.StyledMapType(myStyle, {
		name : 'My Style'
	}));

	var customControlDiv = addCustomControls();
	map.controls[google.maps.ControlPosition.TOP_RIGHT].push(customControlDiv);
	  
	addMapListeners(map);
	return map;
}

function getLatLngForIdx(idx) {
	var latitude = window.android.getCenterLatitude(idx),
		longitude = window.android.getCenterLongitude(idx);
	return getLatLng(latitude, longitude);
}

function getZoomForIdx(idx) {
	var zoomVal = window.android.getZoom(idx);
	return zoomVal;
}

function getPolygon(polygonCoOrd) {
    var polygon = new google.maps.Polygon({
        paths: polygonCoOrd,
        strokeColor: "#FF0000",
        strokeOpacity: 0.8,
        strokeWeight: 1,
        fillColor: "#FF0000",
        fillOpacity: 0.35        
    });
    
    return polygon;
}

/*
*This displays the quizzed location on map 
*/
function displayLocation() {
	var polygonCoOrd = getPolygonCoOrdinates(getIdx());
	gPolygon = getPolygon(polygonCoOrd);
    gPolygon.setMap(gMap);
}

function getPolygonCoOrdinates(idx) {
	var xPoints = new Array(),
	yPoints = new Array(),
	xPointsJSON = window.android.getXPoints(idx), 
	yPointsJSON = window.android.getYPoints(idx);
	
	xPoints = 	JSON.parse(xPointsJSON);
	yPoints = JSON.parse(yPointsJSON);;
	
	console.log(xPoints);
	console.log(yPoints);
	var polygonCoOrd = [
			new google.maps.LatLng(yPoints[0], xPoints[0]),
			new google.maps.LatLng(yPoints[1], xPoints[1]),
			new google.maps.LatLng(yPoints[2], xPoints[2]),
			new google.maps.LatLng(yPoints[3], xPoints[3])
			];
	return polygonCoOrd;
}

function logClickedPoint(latLng) {
	window.android.clickedAt(latLng.lat(), latLng.lng());
}

function getAllLocationsAttr() {
	var allLocAttr = window.android.getAllLocationsAttr();
	console.log("All location attr as obtained in JS is " + allLocAttr);

	var locations = getLocationHolder(allLocAttr);
	
}

//	Parse JSON string into location array
function getLocationHolder(allLocAttr) {
	var locations = new Array();
	locations = JSON.parse(allLocAttr);
	console.log("Number of Locations obtained is :" + locations.length);
	
}