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
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.androidcourse.hw4.R;
import com.androidcourse.hw4.activity.addTranslations.AddTranslation;
import com.androidcourse.hw4.dao.TranslatorDAO;
import com.androidcourse.hw4.dao.TranslatorDAODBImpl;
import com.androidcourse.hw4.factory.TranslationDisplayFactory;
import com.androidcourse.hw4.factory.TranslationDisplayFactoryDBImpl;
import com.androidcourse.hw4.listeners.result.ActivityResultListener;
import com.androidcourse.hw4.util.Category;

public class DisplayTranslation extends Activity {

	private static final int ID_ADD_TRANSLATION = 1;
	TranslationDisplayFactory factory;
	BaseAdapter translationAdapter;
	ActivityResultListener activityResultListener;
	Spinner categorySpinner;
	ListView translationListView;
	Cursor translationCursor;
	TranslatorDAO translatorDAO;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaytranslation);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.displaytranslationmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		boolean handled = false;
		Toast.makeText(this, "onOptionsItemSelected Category selected id: "
				+ getSelectedCategoryId(), Category.TOAST_DURATION)
				.show();
		if (item.getItemId() == R.id.menu_AddTranslation) {
			Intent intent = factory
					.getIntentToAddNewTranslation(AddTranslation.class);
			addDataToIntent(intent);
			this.startActivityForResult(intent, ID_ADD_TRANSLATION);
			handled = true;
		}
		return handled;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		activityResultListener.processResult(requestCode, resultCode, data);
	}

	protected void init() {
		factory = TranslationDisplayFactoryDBImpl.getFactory(this);
		translatorDAO = factory.getTranslatorDAO();
		activityResultListener = factory.getActivityResultListener();
		initCategories();
		initTranslation();
	}

	protected void initCategories() {
		categorySpinner = (Spinner) findViewById(R.id.spinner1);
		Cursor categoryCursor = translatorDAO.getCategoryCursor();
		startManagingCursor(categoryCursor);
		categorySpinner
				.setAdapter(getCategoriesAdapter(categoryCursor));
		categorySpinner.setSelection(Category.DEFAULT_CATEGORY_SELECTION);
		categorySpinner.setOnItemSelectedListener(factory
				.getCategorySelectedListener());
	}

	protected void initTranslation() {
		translationListView = (ListView) findViewById(R.id.listView1);
		refreshTranslation(getSelectedCategoryId());
		/*-TranslationClickListener translationClickListener =(TranslationClickListener) factory
		 .getTranslationClickListener();
		 translationListView
		 .setOnItemClickListener(translationClickListener);
		 */
	}

	protected void refreshTranslation(long selectedCategoryID) {
		translationCursor = translatorDAO
				.getTranslationCursor(selectedCategoryID);
		startManagingCursor(translationCursor);
		translationAdapter = getTranslationAdapter(translationCursor);
		translationListView.setAdapter(translationAdapter);
		// translationAdapter.notifyDataSetChanged();
	}

	private void addDataToIntent(Intent intent) {
		intent.putExtra(getResources().getString(R.string.categoryID),
				getSelectedCategoryId());
	}

	private long getSelectedCategoryId() {
		return categorySpinner.getSelectedItemId();
	}

	public SpinnerAdapter getCategoriesAdapter(Cursor categoryCursor) {
		String[] from = new String[] { TranslatorDAODBImpl.COLUMN_CATEGORY_TYPE };
		int[] to = new int[] { android.R.id.text1 };

		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, categoryCursor, from, to);

		cursorAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return cursorAdapter;
	}

	public BaseAdapter getTranslationAdapter(Cursor translationCursor) {
		String[] from = new String[] {
				TranslatorDAODBImpl.COLUMN_TRANSLATION_LANG1,
				TranslatorDAODBImpl.COLUMN_TRANSLATION_LANG2 };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
				android.R.layout.two_line_list_item, translationCursor, from,
				to);
		return cursorAdapter;
	}

}
