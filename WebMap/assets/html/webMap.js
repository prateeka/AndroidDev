"use strict";

var gMap;
var gMarker;

function mapSingleClickListener(latLng) {
	placeMarker(latLng);
	centerAt(latLng);
	isClickedLocValid(getIdx(), latLng);
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

function getPolygonCoOrdinates() {
	var polygonCoOrd = [
			new google.maps.LatLng(40.99725687752573,-109.05082421875),
			new google.maps.LatLng(37.00337044713457,-109.05082421875),
			new google.maps.LatLng(37.0384570771806,-102.01957421875),
			new google.maps.LatLng(40.96408120506293,-102.10746484375)
			];
	return polygonCoOrd;
}

function displayLocation() {
	var polygonCoOrd = getPolygonCoOrdinates();
	displayPolygon(polygonCoOrd);
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
	return window.android.clickedAt(idx, latLng.lat(), latLng.lng());
}

function initialize() {
	gMap = getMap();
	gMarker = getMarker();
}