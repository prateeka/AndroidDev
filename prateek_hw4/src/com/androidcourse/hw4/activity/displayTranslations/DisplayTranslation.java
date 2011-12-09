package com.androidcourse.hw4.activity.displayTranslations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.androidcourse.hw4.R;
import com.androidcourse.hw4.activity.addTranslations.AddTranslation;
import com.androidcourse.hw4.dao.TranslatorDAO;
import com.androidcourse.hw4.factory.TranslationDisplayFactory;
import com.androidcourse.hw4.factory.TranslationDisplayFactoryDBImpl;
import com.androidcourse.hw4.listeners.result.ActivityResultListener;

public class DisplayTranslation extends Activity {

	private static final int ID_ADD_TRANSLATION = 1;
	TranslationDisplayFactory factory;
	ActivityResultListener activityResultListener;

	TranslatorDAO translatorDAO;
	CategoryHelper categoryHelper;
	TranslationHelper translationHelper;

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
		/*-Toast.makeText(this, "onOptionsItemSelected Category selected id: "
				+ getSelectedCategoryId(), Category.TOAST_DURATION)
				.show();*/
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
		categoryHelper = new CategoryHelper(this, translatorDAO, factory);
		categoryHelper.init();
	}

	protected void initTranslation() {
		translationHelper = new TranslationHelper(this, translatorDAO, factory);
		translationHelper.init();
	}

	private void addDataToIntent(Intent intent) {
		intent.putExtra(getResources().getString(R.string.categoryID),
				getSelectedCategoryId());
	}

	long getSelectedCategoryId() {
		return categoryHelper.getSelectedItemId();
	}

	void refreshTranslation(long selectedCategoryID) {
		translationHelper.refreshTranslation(selectedCategoryID);
	}

}
