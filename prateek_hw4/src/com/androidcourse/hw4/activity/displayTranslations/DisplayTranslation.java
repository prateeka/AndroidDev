package com.androidcourse.hw4.activity.displayTranslations;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.androidcourse.hw4.R;
import com.androidcourse.hw4.factory.TranslationDisplayFactory;
import com.androidcourse.hw4.factory.TranslationDisplayFactoryDBImpl;
import com.androidcourse.hw4.listeners.result.ActivityResultListener;
import com.androidcourse.hw4.util.Category;

public class DisplayTranslation extends Activity {

	private static final int ID_ADD_TRANSLATION = 1;
	TranslationDisplayFactory factory;
	BaseAdapter translationAdapter;
	TranslationClickListener translationClickListener;
	ActivityResultListener activityResultListener;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaytranslation);
		init();
	}

	protected void init() {
		factory = TranslationDisplayFactoryDBImpl.getFactory(this);
		activityResultListener = factory.getActivityResultListener();
		initCategories();
		initTranslation();
	}

	protected void initCategories() {
		Spinner categorySpinner = (Spinner) findViewById(R.id.spinner1);
		Cursor categoryCursor = factory.getCategoryCursor();
		startManagingCursor(categoryCursor);
		categorySpinner
				.setAdapter(factory.getCategoriesAdapter(categoryCursor));
		categorySpinner.setSelection(Category.DEFAULT_CATEGORY_SELECTION);
		categorySpinner.setOnItemSelectedListener(factory
				.getCategorySelectedListener());
	}

	protected void initTranslation() {
		ListView translationListView = (ListView) findViewById(R.id.listView1);
		Cursor translationCursor = factory.getTranslationCursor();

		/*-		translationAdapter = factory
		 .getTranslationAdapter();
		 translationListView
		 .setAdapter(translationAdapter);
		 translationClickListener = (TranslationClickListener) factory
		 .getTranslationClickListener();
		 translationListView
		 .setOnItemClickListener(translationClickListener);
		 */}

	protected void refreshTranslation() {
		translationAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater(); // from activity
		inflater.inflate(R.menu.displaytranslationmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		boolean handled = false;
		if (item.getItemId() == R.id.menu_AddTranslation) {
			this.startActivityForResult(
					factory.getIntentToAddNewTranslation(), ID_ADD_TRANSLATION);
			handled = true;
		}
		return handled;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		activityResultListener.processResult(requestCode, resultCode, data);
	}
}