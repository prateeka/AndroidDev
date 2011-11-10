package com.androidcourse.hw3.activity.displayQuote;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.androidcourse.hw3.R;
import com.androidcourse.hw3.util.Factory;

public class DisplayQuoteResultHandler {

	private static final String TAG = "DisplayActivityResultHandler";
	DisplayQuoteActivity activity;

	public void onActivityResult(Activity activity, int requestCode,
			int resultCode, Intent data) {
		this.activity = (DisplayQuoteActivity) activity;
		Log.d(TAG, "string being retrieved from the intent is : "
				+ activity.getResources().getString(R.string.paramQuoteRating));
		Log.d(TAG, "requestCode : " + requestCode);
		Log.d(TAG, "resultCode : " + resultCode);
		if (requestCode == 0) {
			if (resultCode == Activity.RESULT_OK) {
				processInputParam(data);
			}
		}
	}

	void processInputParam(Intent data) {
		int quoteRating = data.getIntExtra(activity.getResources()
				.getString(R.string.paramQuoteRating),
				Integer.decode(activity.getResources().getString(
						R.string.defaultQuoteRating)));

		int quoteID = data.getIntExtra(
				activity.getResources().getString(
						R.string.paramIDForQuoteToBeRated),
				Integer.decode(activity.getResources().getString(
						R.string.defaultIDForQuote)));

		activity.updateQuoteRatingList(
				quoteID,
				Factory.getDrawableImageMapperInstance()
						.retrieveDrawableImage(activity,
								quoteRating)
				);
		Log.d(TAG, "quoteRating : " + quoteRating);
		activity.refreshAdapter();
	}
}
