package com.actionbarsherlock.sample.shakespeare.activities;

import android.util.Log;

import com.actionbarsherlock.sample.shakespeare.fragments.DetailsFragment;

class DetailHandler {
	private static final String TAG = "TitleSelectedListener";
	
	private static DetailHandler thisInstance;
	
	private DetailHandler() {
	}
	
	static DetailHandler getInstance() {
		if (thisInstance == null) {
			thisInstance = new DetailHandler();
		}
		return thisInstance;
	}
	
	void showDetails(int position) {
		Log.d(TAG, "showDetails position passed is " + position);
		DetailsFragment detailsFragment = DetailsFragment.getInstance();
		
		// ToDO: Handle the case for portrait
		if (detailsFragment != null) {
			detailsFragment.displayDetails(position);
		}
	}
	
	void addNote() {
		Log.d(TAG, "addNote called");
		DetailsFragment detailsFragment = DetailsFragment.getInstance();
		
		// ToDO: Handle the case for portrait
		if (detailsFragment != null) {
			detailsFragment.addNote();
		}
		
	}
}
