package com.androidcourse.hw4.factory;

import android.content.Context;
import android.database.Cursor;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.SpinnerAdapter;

import com.androidcourse.hw4.dao.TranslatorDAODBImpl;
import com.androidcourse.hw4.listeners.result.ActivityResultListener;

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
	public OnItemSelectedListener getCategorySelectedListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActivityResultListener getActivityResultListener() {
		// To Do: see what should come here
		return null;
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
		return translatorDAO.getCategoriesCursor();
	}

	@Override
	public SpinnerAdapter getCategoriesAdapter(Cursor categoryCursor) {
		String[] from = new String[] { TranslatorDAODBImpl.KEY_CATEGORY };
		int[] to = new int[] { android.R.id.text1 };

		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(context,
				android.R.layout.simple_spinner_item, categoryCursor, from, to);

		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		// context, android.R.layout.simple_spinner_item,
		// translatorDAO.getCategories()
		// );
		cursorAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return cursorAdapter;
	}

	@Override
	public BaseAdapter getCategoriesAdapter() {
		throw new RuntimeException("Operation not supported");
	}

}
