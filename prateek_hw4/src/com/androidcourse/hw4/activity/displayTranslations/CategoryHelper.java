package com.androidcourse.hw4.activity.displayTranslations;

import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.androidcourse.hw4.R;
import com.androidcourse.hw4.dao.TranslatorDAO;
import com.androidcourse.hw4.dao.TranslatorDAODBImpl;
import com.androidcourse.hw4.factory.TranslationDisplayFactory;
import com.androidcourse.hw4.util.Category;

public class CategoryHelper {
	Spinner categorySpinner;
	DisplayTranslation activity;
	TranslatorDAO translatorDAO;
	TranslationDisplayFactory factory;

	public CategoryHelper(DisplayTranslation activity,
			TranslatorDAO translatorDAO, TranslationDisplayFactory factory) {
		this.activity = activity;
		this.translatorDAO = translatorDAO;
		this.factory = factory;
	}

	public void init() {
		categorySpinner = (Spinner) activity.findViewById(R.id.spinner1);
		Cursor categoryCursor = translatorDAO.getCategoryCursor();
		activity.startManagingCursor(categoryCursor);
		categorySpinner
				.setAdapter(getCategoriesAdapter(categoryCursor));
		categorySpinner.setSelection(Category.DEFAULT_CATEGORY_SELECTION);
		categorySpinner.setOnItemSelectedListener(factory
				.getCategorySelectedListener());
	}

	public SpinnerAdapter getCategoriesAdapter(Cursor categoryCursor) {
		String[] from = new String[] { TranslatorDAODBImpl.COLUMN_CATEGORY_TYPE };
		int[] to = new int[] { android.R.id.text1 };

		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(activity,
				android.R.layout.simple_spinner_item, categoryCursor, from, to);

		cursorAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return cursorAdapter;
	}

	public long getSelectedItemId() {
		return categorySpinner.getSelectedItemId();
	}

}
