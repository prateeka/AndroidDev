package com.androidcourse.hw4.factory;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.androidcourse.hw4.activity.addTranslations.AddTranslation;
import com.androidcourse.hw4.activity.displayTranslations.CategorySelectedListener;
import com.androidcourse.hw4.activity.displayTranslations.DisplayTranslation;
import com.androidcourse.hw4.activity.displayTranslations.TranslationResultListener;
import com.androidcourse.hw4.dao.TranslatorDAO;
import com.androidcourse.hw4.listeners.result.ActivityResultListener;

public abstract class TranslationDisplayFactory {

	protected static TranslationDisplayFactory factory;
	protected final Context context;
	protected final TranslatorDAO translatorDAO;

	public TranslatorDAO getTranslatorDAO() {
		return translatorDAO;
	}

	protected TranslationDisplayFactory(Context context,
			TranslatorDAO translatorDAO) {
		this.context = context;
		this.translatorDAO = translatorDAO;
	}

	public Intent getIntentToAddNewTranslation(Class<AddTranslation> toActivity) {
		return new Intent(context, toActivity);
	}

	abstract public OnItemClickListener getTranslationClickListener();

	public ActivityResultListener getActivityResultListener() {
		return new TranslationResultListener(
				(DisplayTranslation) context,
				translatorDAO);
	}

	public OnItemSelectedListener getCategorySelectedListener() {
		return new CategorySelectedListener((DisplayTranslation) context);
	}

}
