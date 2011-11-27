package com.androidcourse.hw4.activity.listTranslations;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.androidcourse.hw4.util.Category;

public class TranslationListener implements OnItemClickListener {

	Context context;

	public TranslationListener(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long rowID) {
		Toast.makeText(context, position + " position is clicked ",
				Category.TOAST_DURATION).show();
	}
}