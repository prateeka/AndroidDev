package com.androidcourse.hw4.activity.displayTranslations;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.androidcourse.hw4.dao.TranslatorDAO;
import com.androidcourse.hw4.util.Category;

public class CategorySelectedListener implements OnItemSelectedListener {

	private final DisplayTranslation displayTranslation;
	private final TranslatorDAO translationDAO;

	public CategorySelectedListener(DisplayTranslation displayTranslation,
			TranslatorDAO translationDAO) {
		this.displayTranslation = displayTranslation;
		this.translationDAO = translationDAO;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		Toast.makeText(displayTranslation,
				"Category clicked position:id- " + pos + ":" + id,
				Category.TOAST_DURATION).show();
		displayTranslation.refreshTranslation();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

}
