package com.androidcourse.hw4.factory;

import android.content.Context;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;

import com.androidcourse.hw4.dao.TranslatorDAODBImpl;
import com.androidcourse.hw4.listeners.result.ActivityResultListener;

public class TranslationDisplayFactoryDBImpl extends TranslationDisplayFactory {

	private TranslationDisplayFactoryDBImpl(Context context) {
		// To Do: is context reqd to be passed to TranslatorDAODBImpl
		super(context, new TranslatorDAODBImpl(context));
	}

	static public TranslationDisplayFactory getFactory(Context pContext) {
		if (factory == null) {
			factory = new TranslationDisplayFactoryDBImpl(pContext);
		}
		return factory;
	}

	@Override
	public ActivityResultListener getActivityResultListener() {
		// To Do: see what should come here
		return null;
	}

	@Override
	public SpinnerAdapter getCategoriesAdapter() {
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
	public OnItemSelectedListener getCategorySelectedListener() {
		// TODO Auto-generated method stub
		return null;
	}

}
