package com.androidcourse.hw2.handler;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.androidcourse.hw2.R;
import com.androidcourse.hw2.activity.RatingCaptureActivity;

/*
 * This class acts as a "onClickhandler" delegatee for an RatingCaptureActivity.
 */

public class RatingCaptureClickHandler implements OnItemClickListener {

	RatingCaptureActivity activity;

	Resources resources;
	private static final String TAG = "RatingCaptureActivityClickHandler";

	public RatingCaptureClickHandler(RatingCaptureActivity activity) {
		super();
		this.activity = activity;
		resources = this.activity.getResources();
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long rowID) {
		Log.d(TAG, "position : " + position);
		Log.d(TAG, "rowID : " + rowID);
		Intent intent = activity.getIntent();
		setDataOnIntent(intent, position);
		// checkIntentData(intent);
		returnToParentActivity(intent);
	}

	void checkIntentData(Intent intent) {
		int position = intent.getIntExtra(
				resources.getString(R.string.paramQuoteRating), 100);
		Log.d(TAG, "int form retrieved in checking intent: " + position);

		String rating = intent.getStringExtra(resources
				.getString(R.string.paramQuoteRating));
		Log.d(TAG, "string form retrieved in checking intent: " + rating);
	}

	protected void returnToParentActivity(Intent intent) {
		activity.setResult(Activity.RESULT_OK, intent);
		activity.finish();
	}

	void setDataOnIntent(Intent intent, int position) {
		Log.d(TAG,
				"string being inserted into the intent is : "
						+ resources.getString(R.string.paramQuoteRating));
		intent.putExtra(resources.getString(R.string.paramQuoteRating),
				getSelectedRating(position));
		intent.putExtra(resources.getString(R.string.paramIDForQuoteToBeRated),
				activity.getQuoteID());
	}

	String getSelectedRating(int position) {
		return activity.getRatings().get(position);
	}
}
