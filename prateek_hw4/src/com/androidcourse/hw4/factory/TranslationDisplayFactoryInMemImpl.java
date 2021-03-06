package com.androidcourse.hw4.factory;

import android.content.Context;
import android.database.Cursor;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.SpinnerAdapter;

import com.androidcourse.hw4.R;
import com.androidcourse.hw4.activity.displayTranslations.TranslationClickListener;
import com.androidcourse.hw4.dao.TranslatorDAOInMemImpl;

public class TranslationDisplayFactoryInMemImpl extends
		TranslationDisplayFactory {

	private TranslationDisplayFactoryInMemImpl(Context context) {
		super(context, new TranslatorDAOInMemImpl(context));
	}

	static public TranslationDisplayFactory getFactory(Context pContext) {
		if (factory == null) {
			factory = new TranslationDisplayFactoryInMemImpl(pContext);
		}
		return factory;
	}

	@Override
	public BaseAdapter getCategoriesAdapter() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				context, android.R.layout.simple_spinner_item,
				translatorDAO.getCategories()
				);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}

	@Override
	public BaseAdapter getTranslationAdapter() {
		return new SimpleAdapter(context,
				translatorDAO.getDefaultTranslations(),
				android.R.layout.two_line_list_item, new String[] {
						context.getResources().getString(
								R.string.translationKey),
						context.getResources().getString(
								R.string.translationValue) },
				new int[] { android.R.id.text1, android.R.id.text2 }) {
		};
	}

	@Override
	public OnItemClickListener getTranslationClickListener() {
		return new TranslationClickListener(context);
	}

	@Override
	public Cursor getCategoryCursor() {
		throw new RuntimeException("Operation not supported");
	}

	@Override
	public SpinnerAdapter getCategoriesAdapter(Cursor categoryCursor) {
		throw new RuntimeException("Operation not supported");
	}

	@Override
	public Cursor getTranslationCursor(long selectedCategoryItemID) {
		throw new RuntimeException("Operation not supported");
	}

	@Override
	public BaseAdapter getTranslationAdapter(Cursor translationCursor) {
		throw new RuntimeException("Operation not supported");
	}

}
