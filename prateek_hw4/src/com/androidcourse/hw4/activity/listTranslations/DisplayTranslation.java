package com.androidcourse.hw4.activity.listTranslations;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Spinner;

import com.androidcourse.hw4.R;
import com.androidcourse.hw4.factory.Factory;

public class DisplayTranslation extends Activity {
	Factory factory;

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
		translationListView.setAdapter(factory.getTranslationAdapter());
	}

	protected void initCategories() {
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);

		spinner.setAdapter(factory.getCategoriesAdapter());
		spinner.setOnItemSelectedListener(new SpinnerOnItemSelectedListener(
				this));
		spinner.setSelection(1);
	}

	protected void refreshTranslation(int pos) {
		System.out.println("DisplayTranslation:refreshTranslation called");
	}
}