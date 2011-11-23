package com.androidcourse.hw4.activity.listTranslations;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.androidcourse.hw4.R;
import com.androidcourse.hw4.dao.CategoryDAO;
import com.androidcourse.hw4.factory.Factory;

public class DisplayTranslation extends Activity {
	CategoryDAO categoryDAO;
	Factory factory;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaytranslation);
		init();
	}

	protected void init() {
		factory = Factory.getFactory();
		initSpinner();
	}

	protected void initSpinner() {
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);

		if (categoryDAO == null) {
			categoryDAO = factory.getCategoryDAO();
		}

		spinner.setAdapter(getSpinnerAdapter());
		spinner.setOnItemSelectedListener(new SpinnerOnItemSelectedListener(
				this));
		spinner.setSelection(1);
	}

	protected ArrayAdapter<String> getSpinnerAdapter() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item,
				categoryDAO.getCategories()
				);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}

	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	protected void refreshTranslation(int pos) {
		System.out.println("DisplayTranslation:refreshTranslation called");
	}
}