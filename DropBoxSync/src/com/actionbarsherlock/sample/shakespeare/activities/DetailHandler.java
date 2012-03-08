package com.actionbarsherlock.sample.shakespeare.activities;

import android.util.Log;

import com.actionbarsherlock.sample.shakespeare.fragments.DetailsFragment;

class DetailHandler {
	private static final String TAG = "DetailHandler";
	private static DetailsFragment fragment;
	private static DetailHandler thisInstance;
	
	private DetailHandler() {
	}
	
	static DetailHandler getInstance() {
		if (thisInstance == null) {
			thisInstance = new DetailHandler();
		}
		fragment = DetailsFragment.getInstance();
		return thisInstance;
	}
	
	void showDetails(String titleSelected) {
		Log.d(TAG, "showDetails titleSelected is " + titleSelected);
		
		// ToDO: Handle the case for portrait
		if (fragment != null) {
			fragment.displayDetails(titleSelected);
		}
	}
	
	void addNote() {
		Log.d(TAG, "addNote called");
		
		// ToDO: Handle the case for portrait
		if (fragment != null) {
			fragment.addNote();
		}
	}
	
	void updateNote() {
		Log.d(TAG, "updateNote called");
		
		// ToDO: Handle the case for portrait
		if (fragment != null) {
			fragment.updateNote();
		}
	}
}
