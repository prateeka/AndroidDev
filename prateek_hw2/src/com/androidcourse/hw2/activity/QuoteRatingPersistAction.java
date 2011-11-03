package com.androidcourse.hw2.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.androidcourse.hw2.R;

class QuoteRatingPersistAction {
	DisplayQuoteActivity activity;
	private final String prefsFileName = "PrefFile_Prateek_HW2";

	QuoteRatingPersistAction(DisplayQuoteActivity paramActivity) {
		activity = paramActivity;
	}

	ArrayList<Map<String, String>> getQuotesRatingList() {
		ArrayList<Map<String, String>> quotesRatingList = getQuotesRatingListFromPref();

		if (quotesRatingList == null) {
			quotesRatingList = new ArrayList<Map<String, String>>();

			List<String> quotesList = new ArrayList<String>(
					Arrays.asList(activity.getResources().getStringArray(
							R.array.quotes)));

			for (String quote : quotesList) {
				Map<String, String> quotesRatingMap = activity
						.getQuoteRatingMap(
								quote,
								activity.getRatingList().get(
										activity.getDefaultRatingIndex()));
				quotesRatingList.add(quotesRatingMap);
			}
		}
		return quotesRatingList;
	}

	protected ArrayList<Map<String, String>> getQuotesRatingListFromPref() {
		SharedPreferences sharedPref = getSharedPreferences();
		int numbOfRatingsPersisted = getNumberOfRatingsPersisted(sharedPref);
		if (numbOfRatingsPersisted > 0) {
			return readPersistedPref(sharedPref, numbOfRatingsPersisted);
		} else {
			return null;
		}
	}

	protected ArrayList<Map<String, String>> readPersistedPref(
			SharedPreferences sharedPref, int numbOfRatingsPersisted) {
		ArrayList<Map<String, String>> quotesRatingList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < numbOfRatingsPersisted; i++) {
			String quote = sharedPref.getString(activity.getResources()
					.getString(R.string.paramQuoteStored) + i, "");
			String rating = sharedPref.getString(activity.getResources()
					.getString(R.string.paramRatingStored) + i, "");

			Map<String, String> quotesRatingMap = activity.getQuoteRatingMap(
					quote, rating);
			quotesRatingList.add(quotesRatingMap);
		}
		return quotesRatingList;
	}

	protected SharedPreferences getSharedPreferences() {
		return activity.getSharedPreferences(prefsFileName,
				Context.MODE_PRIVATE);
	}

	protected void persistQuotesRatingList(
			ArrayList<Map<String, String>> quotesRatingList) {
		SharedPreferences sharedPref = getSharedPreferences();
		SharedPreferences.Editor editor = sharedPref.edit();

		persistListCount(editor, quotesRatingList.size());
		for (int i = 0; i < quotesRatingList.size(); i++) {
			persistQuotesRatingMap(editor, quotesRatingList.get(i), i);
		}

		editor.commit();
	}

	protected void persistQuotesRatingMap(Editor editor,
			Map<String, String> quotesRatingMap, int i) {
		editor.putString(
				activity.getResources().getString(R.string.paramQuoteStored)
						+ i,
				quotesRatingMap.get(activity.getResources().getString(
						R.string.paramQuoteKey)));

		editor.putString(
				activity.getResources().getString(R.string.paramRatingStored)
						+ i,
				quotesRatingMap.get(activity.getResources().getString(
						R.string.paramRatingKey)));

	}

	protected void persistListCount(Editor editor, int size) {
		editor.putInt(
				activity.getResources().getString(
						R.string.paramNumberOfQuotesStored), size);
	}

	protected int getNumberOfRatingsPersisted(SharedPreferences sharedPref) {
		return sharedPref.getInt(
				activity.getResources().getString(
						R.string.paramNumberOfQuotesStored), 0);
	}
}
