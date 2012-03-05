package com.actionbarsherlock.sample.shakespeare.activities;

import android.net.Uri;
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
	
	void showDetails(Uri articleUri) {
		DetailsFragment detailsFragment = DetailsFragment.getInstance();
		Log.d(TAG, "articleUripassed is " + articleUri);
		
		// ToDO: Handle the case for portrait
		if (detailsFragment != null) {
			detailsFragment.displayDetails(articleUri);
		}
	}
}
