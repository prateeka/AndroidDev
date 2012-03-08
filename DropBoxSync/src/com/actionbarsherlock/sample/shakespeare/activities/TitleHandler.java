package com.actionbarsherlock.sample.shakespeare.activities;

import com.actionbarsherlock.sample.shakespeare.fragments.TitlesFragment;

class TitleHandler {
	// private static final String TAG = "TitleHandler";
	private static TitlesFragment fragment;
	private static TitleHandler thisInstance;
	
	private TitleHandler() {
	}
	
	static TitleHandler getInstance() {
		if (thisInstance == null) {
			thisInstance = new TitleHandler();
		}
		fragment = TitlesFragment.getInstance();
		return thisInstance;
	}
	
	void refreshTitles() {
		fragment.refreshTitles();
	}
	
}
