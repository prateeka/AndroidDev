package com.androidcourse.hw2.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidcourse.hw2.R;
import com.androidcourse.hw2.handler.RatingCaptureClickHandler;

public class RatingCaptureActivity extends ListActivity {
	private List<String> ratings;
	private ArrayAdapter<String> adapter;
	private int quoteID;
	private String quote;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	protected void init() {
		setContentView(R.layout.ratingcaptureactivity);
		processInputParam();
		displayTheQuoteToBeRated();
		ratings = new ArrayList<String>(Arrays.asList(getResources()
				.getStringArray(R.array.ratings)));
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
		adapter = createAdapter();
		this.setListAdapter(adapter);
	}

	void displayTheQuoteToBeRated() {
		((TextView) findViewById(R.id.ratingCaptureActivityQuote))
				.setText(quote);
	}

	ArrayAdapter<String> createAdapter() {
		return new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, ratings) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView tv = (TextView) super.getView(position, convertView,
						parent);
				tv.setTextSize(15);
				return tv;
			}
		};
	}

	public int getQuoteID() {
		return quoteID;
	}

	public List<String> getRatings() {
		return ratings;
	}
}
