package com.androidcourse.hw4.factory;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;

import com.androidcourse.hw4.R;
import com.androidcourse.hw4.dao.TranslationDAO;
import com.androidcourse.hw4.dao.TranslationDAOInMemImpl;

public class Factory {
	private static Factory factory;
	private TranslationDAO translationDAO;
	private Context context;

	private Factory() {
	}

	static public Factory getFactory(Context pContext) {
		if (factory == null) {
			factory = new Factory();
			factory.setContext(pContext);
		}
		return factory;
	}

	public BaseAdapter getCategoriesAdapter() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getContext(), android.R.layout.simple_spinner_item,
				getTranslationDAO().getCategories()
				);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}

	public BaseAdapter getTranslationAdapter() {
		return new SimpleAdapter(getContext(),
				getTranslationDAO().getTranslations(),
				android.R.layout.two_line_list_item, new String[] {
						getContext().getResources().getString(
								R.string.translationKey),
						getContext().getResources().getString(
								R.string.translationValue) },
				new int[] { android.R.id.text1, android.R.id.text2 }) {
		};
	}

	public TranslationDAO getTranslationDAO() {
		if (translationDAO == null) {
			setTranslationDAO(new TranslationDAOInMemImpl(getContext()));
		}
		return translationDAO;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void setTranslationDAO(TranslationDAO translationDAO) {
		this.translationDAO = translationDAO;
	}
}
