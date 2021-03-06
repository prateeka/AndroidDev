package com.androidcourse.hw4.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import com.androidcourse.hw4.R;
import com.androidcourse.hw4.util.Category;

public class TranslatorDAOInMemImpl implements TranslatorDAO {

	private final List<String> categories;
	private final Context context;
	List<Map<String, String>> translationList = new ArrayList<Map<String, String>>();

	public TranslatorDAOInMemImpl(Context context) {
		this.context = context;
		categories = new ArrayList<String>();
		categories.add(Category.SOCIAL);
		categories.add(Category.MEDICAL);
	}

	@Override
	public List<String> getCategories() {
		return categories;
	}

	@Override
	public List<Map<String, String>> getTranslations(String category) {
		if (category.equals(Category.SOCIAL)) {
			translationList.clear();
			translationList.add(getTranslation("how are you ?",
					"i am doing fine"));
			translationList.add(getTranslation("where are you from?",
					"I am from Asia"));
		} else if (category.equals(Category.MEDICAL)) {
			translationList.clear();
			translationList.add(getTranslation("where is hospital",
					"it is close"));
			translationList.add(getTranslation("where is pharmacy",
					"it is at the mall"));
		}
		return translationList;
	}

	@Override
	public List<Map<String, String>> getDefaultTranslations() {
		return getTranslations(getCategories()
				.get(Category.DEFAULT_CATEGORY_SELECTION));
	}

	private Map<String, String> getTranslation(String lang1, String lang2) {
		Map<String, String> translationMap = new HashMap<String, String>();
		translationMap
				.put(context.getResources().getString(
						R.string.translationKey), lang1);
		translationMap.put(
				context.getResources()
						.getString(R.string.translationValue), lang2);
		return translationMap;
	}

	@Override
	public long addTranslations(Bundle bundle) {
		translationList.add(getTranslation(
				(String) bundle.get(context.getResources()
						.getString(R.string.translationLang1)),
				(String) bundle.get(context
						.getResources()
						.getString(R.string.translationLang2))));

		// Return 0 just to make it compliant with the interface return value.
		// This was incorporated as the DB version could return a value
		return 0;
	}

	@Override
	public Cursor getCategoryCursor() {
		throw new RuntimeException("Operation not supported");
	}

	@Override
	public Cursor getTranslationCursor(long selectedCategoryItemID) {
		throw new RuntimeException("Operation not supported");
	}
}
