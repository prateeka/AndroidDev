package com.androidcourse.hw4.activity.displayTranslations;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class CategorySelectedListener implements OnItemSelectedListener {

	private final DisplayTranslation displayTranslation;

	public CategorySelectedListener(DisplayTranslation displayTranslation) {
		this.displayTranslation = displayTranslation;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		/*-		Toast.makeText(displayTranslation,
		 "Category clicked position:id- " + pos + ":" + id,
		 Category.TOAST_DURATION).show();
		 */displayTranslation.refreshTranslation(id);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

}
