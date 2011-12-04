package com.androidcourse.hw4.factory;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;

import com.androidcourse.hw4.activity.addTranslations.AddTranslation;
import com.androidcourse.hw4.dao.TranslatorDAO;
import com.androidcourse.hw4.listeners.result.ActivityResultListener;

public abstract class TranslationDisplayFactory {

	protected static TranslationDisplayFactory factory;
	protected final Context context;
	protected final TranslatorDAO translatorDAO;

	protected TranslationDisplayFactory(Context context,
			TranslatorDAO translatorDAO) {
		this.context = context;
		this.translatorDAO = translatorDAO;
	}

	abstract public ActivityResultListener getActivityResultListener();

	abstract public OnItemClickListener getTranslationClickListener();

	abstract public BaseAdapter getTranslationAdapter();

	abstract public BaseAdapter getCategoriesAdapter();

	abstract public SpinnerAdapter getCategoriesAdapter(Cursor categoryCursor);

	abstract public Cursor getCategoryCursor();

	abstract public Cursor getTranslationCursor();

	abstract public OnItemSelectedListener getCategorySelectedListener();

	public Intent getIntentToAddNewTranslation() {
		return new Intent(context, AddTranslation.class);
	}

}
