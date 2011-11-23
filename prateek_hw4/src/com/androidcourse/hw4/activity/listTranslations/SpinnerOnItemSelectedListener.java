package com.androidcourse.hw4.activity.listTranslations;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SpinnerOnItemSelectedListener implements OnItemSelectedListener {

	private final DisplayTranslation displayTranslation;

	public SpinnerOnItemSelectedListener(DisplayTranslation displayTranslation) {
		this.displayTranslation = displayTranslation;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		System.out
				.println("SpinnerOnItemSelectedListener:onItemSelected called");
		displayTranslation.refreshTranslation(pos);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		System.out
				.println("SpinnerOnItemSelectedListener:onNothingSelected called");
	}

}
