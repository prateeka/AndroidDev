"use strict";

var gMap;
var gMarker;
var gPolygon;

function mapSingleClickListener(latLng) {
	placeMarker(latLng);
	centerAt(latLng);
	logClickedPoint(latLng);
}

function displayClickResultMessage() {
	var dummyLatLng = gMarker.getPosition(), 
		validSelection = isClickedLocValid(getIdx(), dummyLatLng), 
		selectionMessage;
	
	if (validSelection) {
		selectionMessage = '<strong>Correct selection<strong>';
	} else {
		selectionMessage = '<strong>Incorrect selection.<strong>';
	}
	return selectionMessage;
}

function customControlClickListener(controlDiv, lockButton) {
	var selectionMessage = displayClickResultMessage();
	
	var answerLabel = addControl(selectionMessage);
	controlDiv.appendChild(answerLabel);
	
	var nextButton = addControl('<strong>Next<strong>');
	controlDiv.appendChild(nextButton);

	lockButton.style.visibility = 'hidden';	

	displayLocation();
}

function isClickedLocValid(idx, latLng) {
	var polygonCoOrd = getPolygonCoOrdinates(getIdx());
	var polygon = getPolygon(polygonCoOrd);
	
	var isInsidePolygon = google.maps.geometry.poly.containsLocation(latLng, polygon);
	console.log(isInsidePolygon);
	return isInsidePolygon; 
}

function initialize() {
	gMap = getMap();
	gMarker = getMarker();
}