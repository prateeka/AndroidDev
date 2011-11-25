package com.androidcourse.hw4.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.androidcourse.hw4.R;
import com.androidcourse.hw4.util.Category;

public class TranslationDAOInMemImpl implements TranslationDAO {

	private final List<String> categories;
	private final Context context;

	public TranslationDAOInMemImpl(Context context) {
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
	public List<Map<String, String>> getTranslations() {
		List<Map<String, String>> translationList;
		translationList = getTranslationList(Category.SOCIAL);
		// translationList = getTranslationList(Category.MEDICAL);
		return translationList;
	}

	protected List<Map<String, String>> getTranslationList(
			String category) {
		List<Map<String, String>> translationList = new ArrayList<Map<String, String>>();
		if (category.equals(Category.SOCIAL)) {
			translationList.add(getTranslation("where is hospital",
					"it is close"));
			translationList.add(getTranslation("where is pharmacy",
					"it is at the mall"));
		}
		return translationList;
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

}
