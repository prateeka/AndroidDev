package com.androidcourse.hw2.handler;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.androidcourse.hw2.R;
import com.androidcourse.hw2.activity.DisplayQuoteActivity;
import com.androidcourse.hw2.activity.RatingCaptureActivity;

/*
 * This class acts as a "onClickhandler" delegatee for an activity. The
 * delegator activity is also passed to this handler so that the handler can
 * access the required data elements of the delegator activity.
 */
public class DisplayQuoteClickHandler implements OnItemClickListener {

	DisplayQuoteActivity activity;
	Resources resources;
	private static final String TAG = "DisplayQuoteActivityClickHandler";

	public DisplayQuoteClickHandler(DisplayQuoteActivity activity) {
		super();
		this.activity = activity;
		resources = activity.getResources();
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long rowID) {
		/*-String quoteToBeRated = getQuoteToBeRated(position);
		 startRatingActivity(quoteToBeRated);
		 */
		Intent intent = getPreparedIntent(position);
		startRatingActivity(intent);
	}

	Intent getPreparedIntent(int position) {
		Intent intent = getStartActivityIntent(activity,
				RatingCaptureActivity.class);
		setDataOnIntent(intent, position);
		return intent;
	}

	private Intent getStartActivityIntent(Activity fromActivity,
			Class<RatingCaptureActivity> toActivityClass) {
		return new Intent(fromActivity, toActivityClass);
	}

	void setDataOnIntent(Intent intent, int position) {
		String quote = getSelectedQuote(position);
		String rating = getSelectedRating(position);
		intent.putExtra(resources.getString(R.string.paramQuoteToBeRated),
				quote);
		intent.putExtra(resources
				.getString(R.string.paramExistingRatingForQuoteToBeRated),
				rating);
		intent.putExtra(resources.getString(R.string.paramIDForQuoteToBeRated),
				position);
	}

	private String getSelectedRating(int position) {
		return activity
				.getQuotesRatingList()
				.get(position)
				.get(activity.getResources().getString(R.string.paramRatingKey));
	}

	private String getSelectedQuote(int position) {
		return activity.getQuotesRatingList().get(position)
				.get(activity.getResources().getString(R.string.paramQuoteKey));
	}

	private void startRatingActivity(Intent intent) {
		Log.d(TAG,
				"paramDisplayToRatingCallID : "
						+ Integer.decode(resources
								.getString(R.string.paramDisplayActivityToRatingActivityCallID)));
		activity.startActivityForResult(
				intent,
				Integer.decode(resources
						.getString(R.string.paramDisplayActivityToRatingActivityCallID)));
	}
}
