package com.androidcourse.hw4.activity.displayTranslations;

import android.database.Cursor;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.androidcourse.hw4.R;
import com.androidcourse.hw4.dao.TranslatorDAO;
import com.androidcourse.hw4.dao.TranslatorDAODBImpl;
import com.androidcourse.hw4.factory.TranslationDisplayFactory;

public class TranslationHelper {
	DisplayTranslation activity;
	TranslatorDAO translatorDAO;
	TranslationDisplayFactory factory;

	ListView translationListView;

	public TranslationHelper(DisplayTranslation activity,
			TranslatorDAO translatorDAO, TranslationDisplayFactory factory) {
		super();
		this.activity = activity;
		this.translatorDAO = translatorDAO;
		this.factory = factory;
	}

	Cursor translationCursor;
	BaseAdapter translationAdapter;

	public void init() {
		translationListView = (ListView) activity.findViewById(R.id.listView1);
		refreshTranslation(activity.getSelectedCategoryId());
		/*-TranslationClickListener translationClickListener =(TranslationClickListener) factory
		 .getTranslationClickListener();
		 translationListView
		 .setOnItemClickListener(translationClickListener);
		 */
	}

	protected void refreshTranslation(long selectedCategoryID) {
		translationCursor = translatorDAO
				.getTranslationCursor(selectedCategoryID);
		activity.startManagingCursor(translationCursor);
		translationAdapter = getTranslationAdapter(translationCursor);
		translationListView.setAdapter(translationAdapter);
		// translationAdapter.notifyDataSetChanged();
	}

	public BaseAdapter getTranslationAdapter(Cursor translationCursor) {
		String[] from = new String[] {
				TranslatorDAODBImpl.COLUMN_TRANSLATION_LANG1,
				TranslatorDAODBImpl.COLUMN_TRANSLATION_LANG2 };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(activity,
				android.R.layout.two_line_list_item, translationCursor, from,
				to);
		return cursorAdapter;
	}

}
