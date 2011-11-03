package com.androidcourse.hw2.handler;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.androidcourse.hw2.R;
import com.androidcourse.hw2.activity.DisplayQuoteActivity;

public class DisplayQuoteResultHandler implements IOnActivityResultHandler {

	private static final String TAG = "DisplayActivityResultHandler";
	DisplayQuoteActivity activity;

	@Override
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
				// quoteRating = retrieveIntentIntData(data);
			}
		}
	}

	/*-
	 protected int retrieveIntentIntData(Intent data) {
	 return data.getIntExtra(
	 activity.getResources().getString(R.string.paramQuoteRating),
	 Integer.parseInt(activity.getResources().getString(
	 R.string.defaultQuoteRatingIndex)));
	 }
	 */
	void processInputParam(Intent data) {
		String quoteRating = data.getStringExtra(activity.getResources()
				.getString(R.string.paramQuoteRating));
		int quoteID = data.getIntExtra(
				activity.getResources().getString(
						R.string.paramIDForQuoteToBeRated),
				Integer.decode(activity.getResources().getString(
						R.string.defaultIDForQuote)));

		activity.setQuoteRating(quoteID, quoteRating);
		Log.d(TAG, "quoteRating : " + quoteRating);
		activity.refreshAdapter();
	}
}
