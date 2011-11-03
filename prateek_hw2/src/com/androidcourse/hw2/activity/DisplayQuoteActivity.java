package com.androidcourse.hw2.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleAdapter;

import com.androidcourse.hw2.R;
import com.androidcourse.hw2.handler.DisplayQuoteClickHandler;
import com.androidcourse.hw2.handler.DisplayQuoteResultHandler;
import com.androidcourse.hw2.handler.IOnActivityResultHandler;

/*
 * This activity displays the quotes with rankings. This activity also displays
 * a Edit text box to add new quotes. When clicked on on each qoute, a new
 * activity is opened to enter rankings.
 */

public class DisplayQuoteActivity extends ListActivity {

	private ArrayList<Map<String, String>> quotesRatingList = new ArrayList<Map<String, String>>();

	private EditText newQuoteTextID;
	private SimpleAdapter adapter;
	private List<String> ratingList;

	private QuoteRatingPersistAction quoteRatingPersistAction;

	private static final String TAG = "DisplayQuoteActivity";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	protected void init() {
		setContentView(R.layout.displayquoteactivity);
		initializeRatingList();
		initializeQuoteRatingList();
		newQuoteTextID = (EditText) findViewById(R.id.newQuoteText);
		initializeListView();
	}

	private void initializeRatingList() {
		ratingList = new ArrayList<String>(Arrays.asList(getResources()
				.getStringArray(R.array.ratings)));
	}

	protected void initializeQuoteRatingList() {
		quoteRatingPersistAction = new QuoteRatingPersistAction(this);
		quotesRatingList = quoteRatingPersistAction.getQuotesRatingList();
		/*-		quotesList = new ArrayList<String>(Arrays.asList(getResources()
		 .getStringArray(R.array.quotes)));
		 for (String quote : quotesList) {
		 Map<String, String> quotesRatingMap = getQuoteRatingMap(quote,
		 ratingList.get(getDefaultRatingIndex()));
		 quotesRatingList.add(quotesRatingMap);
		 }
		 */
	}

	protected int getDefaultRatingIndex() {
		return Integer.parseInt(getResources().getString(
				R.string.defaultQuoteRatingIndex));
	}

	protected Map<String, String> getQuoteRatingMap(String quote, String rating) {
		Map<String, String> quotesRatingMap = new HashMap<String, String>();
		quotesRatingMap.put(getResources().getString(R.string.paramQuoteKey),
				quote);
		quotesRatingMap.put(getResources().getString(R.string.paramRatingKey),
				rating);
		return quotesRatingMap;
	}

	protected void initializeListView() {
		getListView()
				.setOnItemClickListener(new DisplayQuoteClickHandler(this));
		adapter = createAdapter();
		this.setListAdapter(adapter);
	}

	protected SimpleAdapter createAdapter() {
		return new SimpleAdapter(this, quotesRatingList,
				android.R.layout.two_line_list_item, new String[] {
						getResources().getString(R.string.paramQuoteKey),
						getResources().getString(R.string.paramRatingKey) },
				new int[] { android.R.id.text1, android.R.id.text2 }) {
			/*-@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView tv = (TextView) super.getView(position, convertView,
						parent);
				tv.setTextSize(10);
				tv.setHeight(5);
				return tv;
			}*/
		};
	}

	public void onButtonClick(View view) {
		updateQuoteList();
		setNewQuoteText("");
		refreshAdapter();
	}

	public void setNewQuoteText(String text) {
		newQuoteTextID.setText(text);
	}

	protected void updateQuoteList() {
		String newQuote = retrieveNewQuote();
		addNewQuote(newQuote);
	}

	private void addNewQuote(String newQuote) {
		Map<String, String> quoteRatingMap = getQuoteRatingMap(newQuote,
				ratingList.get(getDefaultRatingIndex()));
		quotesRatingList.add(quoteRatingMap);
	}

	protected String retrieveNewQuote() {
		String newQuote = newQuoteTextID.getText().toString();
		return newQuote;
	}

	public void refreshAdapter() {
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		IOnActivityResultHandler activityResultHandler = new DisplayQuoteResultHandler();
		activityResultHandler.onActivityResult(this, requestCode, resultCode,
				data);
	}

	public ArrayList<Map<String, String>> getQuotesRatingList() {
		return quotesRatingList;
	}

	public void setQuoteRating(int quoteID, String quoteRating) {
		Map<String, String> quoteRatingMap = quotesRatingList.get(quoteID);
		quoteRatingMap.put(getResources().getString(R.string.paramRatingKey),
				quoteRating);
	}

	public List<String> getRatingList() {
		return ratingList;
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG, "onPause called");
		quoteRatingPersistAction.persistQuotesRatingList(quotesRatingList);
	}
}
