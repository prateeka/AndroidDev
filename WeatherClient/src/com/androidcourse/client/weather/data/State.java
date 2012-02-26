package com.androidcourse.client.weather.data;

/*
 * This class represents the various states of downloading weather data.
 */
public enum State {
	INACTIVE,
	DOWNLOADING,
	TEXT_DOWNLOADED,
	READY,
	INVALID
}
