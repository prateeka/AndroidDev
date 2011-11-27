package com.androidcourse.hw4.activity.displayTranslations;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.androidcourse.hw4.util.Category;

public class TranslationClickListener implements OnItemClickListener {

	Context context;

	public TranslationClickListener(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long rowID) {
		Toast.makeText(context, position + " No action defined",
				Category.TOAST_DURATION).show();
	}

}