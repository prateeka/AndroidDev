"use strict";

var gMap;
var gMarker;

function mapSingleClickListener(latLng) {
	placeMarker(latLng);
	centerAt(latLng);
	isClickedLocValid(latLng);
}

function getMessageToDisplay() {
	var dummyLatLng = gMarker.getPosition(), 
		validSelection = isClickedLocValid(dummyLatLng), 
		selectionMessage;
	
	if (validSelection) {
		selectionMessage = '<strong>Correct selection<strong>';
	} else {
		selectionMessage = '<strong>Incorrect selection.<strong>';
	}
	return selectionMessage;
}

function getPolygon() {
	
}

function displayLocation() {
//	var polygon = getPolygon();
// displayPolygon(polygon);
}

function customControlClickListener(controlDiv, lockButton) {
	var selectionMessage = getMessageToDisplay();
	
	var answerLabel = addControl(selectionMessage);
	controlDiv.appendChild(answerLabel);
	
	var nextButton = addControl('<strong>Next<strong>');
	controlDiv.appendChild(nextButton);

	lockButton.style.visibility = 'hidden';	

//	displayLocation();
}

function initialize() {
	gMap = getMap();
	gMarker = getMarker();
}