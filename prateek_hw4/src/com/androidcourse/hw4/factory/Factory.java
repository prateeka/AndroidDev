package com.androidcourse.hw4.factory;

import android.content.Context;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;

import com.androidcourse.hw4.R;
import com.androidcourse.hw4.activity.listTranslations.CategoryListener;
import com.androidcourse.hw4.activity.listTranslations.DisplayTranslation;
import com.androidcourse.hw4.activity.listTranslations.TranslationListener;
import com.androidcourse.hw4.dao.TranslatorDAO;
import com.androidcourse.hw4.dao.TranslatorDAOInMemImpl;

public class Factory {
	private static Factory factory;
	private final TranslatorDAO translatorDAO;
	private final Context context;

	private Factory(Context pContext) {
		context = pContext;
		translatorDAO = new TranslatorDAOInMemImpl(pContext);

	}

	static public Factory getFactory(Context pContext) {
		if (factory == null) {
			factory = new Factory(pContext);
		}
		return factory;
	}

	public BaseAdapter getCategoriesAdapter() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				context, android.R.layout.simple_spinner_item,
				translatorDAO.getCategories()
				);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}

	public OnItemSelectedListener getCategoryListener(
			DisplayTranslation displayTranslation) {
		return new CategoryListener(displayTranslation, translatorDAO);
	}

	public BaseAdapter getTranslationAdapter() {
		return new SimpleAdapter(context,
				translatorDAO.getTranslations(),
				android.R.layout.two_line_list_item, new String[] {
						context.getResources().getString(
								R.string.translationKey),
						context.getResources().getString(
								R.string.translationValue) },
				new int[] { android.R.id.text1, android.R.id.text2 }) {
		};
	}

	public OnItemClickListener getTranslationListener() {
		return new TranslationListener(context);
	}

}
