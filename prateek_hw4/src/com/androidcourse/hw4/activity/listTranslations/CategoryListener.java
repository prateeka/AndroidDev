package com.androidcourse.hw4.activity.listTranslations;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.androidcourse.hw4.dao.TranslatorDAO;

public class CategoryListener implements OnItemSelectedListener {

	private final DisplayTranslation displayTranslation;
	private final TranslatorDAO translationDAO;

	public CategoryListener(DisplayTranslation displayTranslation,
			TranslatorDAO translationDAO) {
		this.displayTranslation = displayTranslation;
		this.translationDAO = translationDAO;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		translationDAO.getTranslations(translationDAO.getCategories().get(pos));
		displayTranslation.refreshTranslation(pos);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

}
