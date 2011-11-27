package com.androidcourse.hw4.activity.listTranslations;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.androidcourse.hw4.R;
import com.androidcourse.hw4.factory.Factory;
import com.androidcourse.hw4.util.Category;

public class DisplayTranslation extends Activity {

	Factory factory;
	BaseAdapter translationAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaytranslation);
		init();
	}

	protected void init() {
		factory = Factory.getFactory(this);
		initCategories();
		initTranslation();
	}

	protected void initTranslation() {
		ListView translationListView = (ListView) findViewById(R.id.listView1);
		translationAdapter = factory
				.getTranslationAdapter();
		translationListView
				.setAdapter(translationAdapter);
		translationListView
				.setOnItemClickListener(factory.getTranslationListener());
	}

	protected void initCategories() {
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);

		spinner.setAdapter(factory.getCategoriesAdapter());
		spinner.setOnItemSelectedListener(factory.getCategoryListener(this));
		spinner.setSelection(Category.DEFAULT_CATEGORY_SELECTION);
	}

	protected void refreshTranslation(int pos) {
		translationAdapter.notifyDataSetChanged();
	}
}