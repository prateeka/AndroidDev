package com.androidcourse.hw4.factory;

import android.content.Context;
import android.database.Cursor;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.SpinnerAdapter;

import com.androidcourse.hw4.dao.TranslatorDAODBImpl;

public class TranslationDisplayFactoryDBImpl extends TranslationDisplayFactory {

	static public TranslationDisplayFactory getFactory(Context pContext) {
		if (factory == null) {
			factory = new TranslationDisplayFactoryDBImpl(pContext);
		}
		return factory;
	}

	private TranslationDisplayFactoryDBImpl(Context context) {
		// To Do: is context reqd to be passed to TranslatorDAODBImpl
		super(context, new TranslatorDAODBImpl(context));
	}

	@Override
	public OnItemClickListener getTranslationClickListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseAdapter getTranslationAdapter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cursor getCategoryCursor() {
		return translatorDAO.getCategoryCursor();
	}

	@Override
	public Cursor getTranslationCursor(long selectedCategoryItemID) {
		return translatorDAO.getTranslationCursor(selectedCategoryItemID);
	}

	@Override
	public SpinnerAdapter getCategoriesAdapter(Cursor categoryCursor) {
		String[] from = new String[] { TranslatorDAODBImpl.COLUMN_CATEGORY_TYPE };
		int[] to = new int[] { android.R.id.text1 };

		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(context,
				android.R.layout.simple_spinner_item, categoryCursor, from, to);

		cursorAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return cursorAdapter;
	}

	@Override
	public BaseAdapter getTranslationAdapter(Cursor translationCursor) {
		String[] from = new String[] {
				TranslatorDAODBImpl.COLUMN_TRANSLATION_LANG1,
				TranslatorDAODBImpl.COLUMN_TRANSLATION_LANG2 };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(context,
				android.R.layout.two_line_list_item, translationCursor, from,
				to);
		return cursorAdapter;
	}

	@Override
	public BaseAdapter getCategoriesAdapter() {
		throw new RuntimeException("Operation not supported");
	}

}
