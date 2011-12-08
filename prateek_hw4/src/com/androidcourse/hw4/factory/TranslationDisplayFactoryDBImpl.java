package com.androidcourse.hw4.factory;

import android.content.Context;
import android.widget.AdapterView.OnItemClickListener;

import com.androidcourse.hw4.dao.TranslatorDAODBImpl;

public class TranslationDisplayFactoryDBImpl extends TranslationDisplayFactory {

	static public TranslationDisplayFactory getFactory(Context pContext) {
		if (factory == null) {
			factory = new TranslationDisplayFactoryDBImpl(pContext);
		}
		return factory;
	}

	private TranslationDisplayFactoryDBImpl(Context context) {
		super(context, new TranslatorDAODBImpl(context));
	}

	@Override
	public OnItemClickListener getTranslationClickListener() {
		// TODO Auto-generated method stub
		return null;
	}

}
