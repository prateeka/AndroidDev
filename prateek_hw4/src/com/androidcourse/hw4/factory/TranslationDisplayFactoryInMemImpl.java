package com.androidcourse.hw4.factory;

import android.content.Context;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;

import com.androidcourse.hw4.R;
import com.androidcourse.hw4.activity.displayTranslations.CategorySelectedListener;
import com.androidcourse.hw4.activity.displayTranslations.DisplayTranslation;
import com.androidcourse.hw4.activity.displayTranslations.DisplayTranslationResultListener;
import com.androidcourse.hw4.activity.displayTranslations.TranslationClickListener;
import com.androidcourse.hw4.dao.TranslatorDAOInMemImpl;
import com.androidcourse.hw4.listeners.result.ActivityResultListener;

public class TranslationDisplayFactoryInMemImpl extends
		TranslationDisplayFactory {
	private static TranslationDisplayFactoryInMemImpl factory;

	private TranslationDisplayFactoryInMemImpl(Context context) {
		super(context, new TranslatorDAOInMemImpl(context));
	}

	static public TranslationDisplayFactoryInMemImpl getFactory(Context pContext) {
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
	public OnItemSelectedListener getCategorySelectedListener() {
		return new CategorySelectedListener((DisplayTranslation) context,
				translatorDAO);
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
	public ActivityResultListener getActivityResultListener() {
		return new DisplayTranslationResultListener(
				(DisplayTranslation) context,
				translatorDAO);
	}

}
