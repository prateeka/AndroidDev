package com.actionbarsherlock.sample.shakespeare.activities;

import android.util.Log;

import com.actionbarsherlock.sample.shakespeare.fragments.DetailsFragment;

class TitleSelectedListener {
	private static final String TAG = "TitleSelectedListener ";
	
	private static TitleSelectedListener thisInstance;
	
	private TitleSelectedListener() {
	}
	
	static TitleSelectedListener getInstance() {
		if (thisInstance == null) {
			thisInstance = new TitleSelectedListener();
		}
		return thisInstance;
	}
	
	void showDetails(int position) {
		DetailsFragment detailsFragment = DetailsFragment.getInstance();
		Log.d(TAG, "position passed is " + position);
		
		// ToDO: Handle the case for portrait
		if (detailsFragment != null) {
			detailsFragment.displayDetails(position);
		}
	}
}
