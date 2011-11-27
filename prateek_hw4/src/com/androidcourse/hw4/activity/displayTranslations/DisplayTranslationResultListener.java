package com.androidcourse.hw4.activity.displayTranslations;

import android.app.Activity;
import android.content.Intent;

import com.androidcourse.hw4.dao.TranslatorDAO;
import com.androidcourse.hw4.listeners.result.ActivityResultListener;

public class DisplayTranslationResultListener implements ActivityResultListener {
	private final DisplayTranslation displayTranslation;
	private final TranslatorDAO translationDAO;

	public DisplayTranslationResultListener(
			DisplayTranslation displayTranslation, TranslatorDAO translationDAO) {
		super();
		this.displayTranslation = displayTranslation;
		this.translationDAO = translationDAO;
	}

	@Override
	public void processResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			translationDAO.addTranslations(data.getExtras());
			displayTranslation.refreshTranslation();
		}
	}
}
