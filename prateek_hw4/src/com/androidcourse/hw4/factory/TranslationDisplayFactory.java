package com.androidcourse.hw4.factory;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;

import com.androidcourse.hw4.R;
import com.androidcourse.hw4.activity.addTranslations.AddTranslation;
import com.androidcourse.hw4.activity.displayTranslations.CategorySelectedListener;
import com.androidcourse.hw4.activity.displayTranslations.DisplayTranslation;
import com.androidcourse.hw4.activity.displayTranslations.DisplayTranslationResultListener;
import com.androidcourse.hw4.activity.displayTranslations.TranslationClickListener;
import com.androidcourse.hw4.dao.TranslatorDAO;
import com.androidcourse.hw4.dao.TranslatorDAOInMemImpl;
import com.androidcourse.hw4.listeners.result.ActivityResultListener;

public class TranslationDisplayFactory {
	private static TranslationDisplayFactory factory;
	private final TranslatorDAO translatorDAO;
	private final Context context;

	private TranslationDisplayFactory(Context pContext) {
		context = pContext;
		translatorDAO = new TranslatorDAOInMemImpl(pContext);

	}

	static public TranslationDisplayFactory getFactory(Context pContext) {
		if (factory == null) {
			factory = new TranslationDisplayFactory(pContext);
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

	public OnItemSelectedListener getCategorySelectedListener() {
		return new CategorySelectedListener((DisplayTranslation) context,
				translatorDAO);
	}

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

	public OnItemClickListener getTranslationClickListener() {
		return new TranslationClickListener(context);
	}

	public Intent getIntentToAddNewTranslation() {
		return new Intent(context, AddTranslation.class);
	}

	public ActivityResultListener getActivityResultListener() {
		return new DisplayTranslationResultListener(
				(DisplayTranslation) context,
				translatorDAO);
	}

}
