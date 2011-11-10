package com.androidcourse.hw3.activity.ratingCapture;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.androidcourse.hw3.R;
import com.androidcourse.hw3.adapter.CustomRatingAdapter;

public class RatingCaptureActivity extends ListActivity {
	private int quoteID;
	private String quote;
	private static final String TAG = "RatingCaptureActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	protected void init() {
		setContentView(R.layout.ratingcaptureactivity);
		processInputParam();
		displayTheQuoteToBeRated();
		initializeListView();
	}

	private void processInputParam() {
		quoteID = getIntent().getIntExtra(
				this.getResources()
						.getString(R.string.paramIDForQuoteToBeRated), 0);
		quote = getIntent().getStringExtra(
				this.getResources().getString(R.string.paramQuoteToBeRated));
	}

	protected void initializeListView() {
		getListView().setOnItemClickListener(
				new RatingCaptureClickHandler(this));
		this.setListAdapter(new CustomRatingAdapter(this,
				Integer.parseInt(getResources().getString(
						R.string.numberOfRatings))));
	}

	void displayTheQuoteToBeRated() {
		((TextView) findViewById(R.id.ratingCaptureActivityQuote))
				.setText(quote);
	}

	public int getQuoteID() {
		return quoteID;
	}

}
