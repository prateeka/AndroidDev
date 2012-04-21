var map;
var marker;

function initialize() {
	var latitude = 0;
	var longitude = 0;
	if (window.android) {
		latitude = window.android.getLatitude();
		longitude = window.android.getLongitude();
	}

	var myStyle = [ {
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

	var myLatlng = new google.maps.LatLng(latitude, longitude);
	var myOptions = {
		zoom : 4,
		center : myLatlng,
		mapTypeControlOptions : {
			mapTypeIds : [ 'mystyle', google.maps.MapTypeId.ROADMAP,
					google.maps.MapTypeId.TERRAIN ]
		},
		mapTypeId : 'mystyle'
	};
	map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
	map.mapTypes.set('mystyle', new google.maps.StyledMapType(myStyle, {
		name : 'My Style'
	}));

	marker = new google.maps.Marker({
		position : map.getCenter(),
		map : map,
		title : 'Click to zoom'
	});

	google.maps.event.addListener(map, 'click', function(event) {
		placeMarker(event.latLng);
	});
}

function centerAt(latitude, longitude) {
	myLatlng = new google.maps.LatLng(latitude, longitude);
	map.panTo(myLatlng);
}

function placeMarker(location) {
	marker.setPosition(location);
	map.panTo(location);
	window.android.clicked(location.lat(), location.lng());
}
