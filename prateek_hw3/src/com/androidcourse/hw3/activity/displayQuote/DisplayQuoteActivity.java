package com.androidcourse.hw3.activity.displayQuote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.androidcourse.hw3.R;
import com.androidcourse.hw3.adapter.CustomQuoteRatingAdapter;
import com.androidcourse.hw3.vo.QuoteRatingVO;

/*
 * This activity displays the quotes with rankings. This activity also displays
 * a Edit text box to add new quotes. When clicked on on each qoute, a new
 * activity is opened to enter rankings.
 */

public class DisplayQuoteActivity extends ListActivity {

	private ArrayList<QuoteRatingVO> ratedQuotesVOList = new ArrayList<QuoteRatingVO>();

	private EditText newQuoteTextID;

	private RatedQuotePersistAction ratedQuotePersistAction;

	private DisplayQuoteResultHandler displayQuoteResultHandler;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	protected void init() {
		setContentView(R.layout.displayquoteactivity);
		newQuoteTextID = (EditText) findViewById(R.id.newQuoteText);
		displayQuoteResultHandler = new DisplayQuoteResultHandler();
		ratedQuotesVOList = retrieveInitializedRatedQuotesList();
		initializeListView();
	}

	protected ArrayList<QuoteRatingVO> retrieveInitializedRatedQuotesList() {
		ratedQuotePersistAction = new RatedQuotePersistAction(this);
		return ratedQuotePersistAction.getRatedQuotesList();
	}

	protected void initializeListView() {
		getListView()
				.setOnItemClickListener(new DisplayQuoteClickHandler(this));
		this.setListAdapter(new CustomQuoteRatingAdapter(this,
				ratedQuotesVOList));
	}

	/*
	 * This method delgates the rating activity to
	 * DisplayQuoteResultHandler(..). When the user clicks on one of the quotes,
	 * then tihs method invoked resulting in delegation. The end result of
	 * delegation is initiation of RatingCapturActivity to capture the quote
	 * rating.
	 * (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		displayQuoteResultHandler.onActivityResult(this, requestCode,
				resultCode,
				data);
	}

	public void onButtonClick(View view) {
		updateQuoteRatingList();
		setNewQuoteText("");
	}

	protected Map<String, String> getQuoteRatingMap(String quote, String rating) {
		Map<String, String> quotesRatingMap = new HashMap<String, String>();
		quotesRatingMap.put(getResources().getString(R.string.paramQuoteKey),
				quote);
		quotesRatingMap.put(getResources().getString(R.string.paramRatingKey),
				rating);
		return quotesRatingMap;
	}

	protected QuoteRatingVO getNewQuoteRatingVO(String quote, Drawable image) {
		return new QuoteRatingVO(quote, image);
	}

	public void setNewQuoteText(String text) {
		newQuoteTextID.setText(text);
	}

	protected void updateQuoteRatingList() {
		ratedQuotesVOList.add(getNewQuoteRatingVO(retrieveNewQuote(),
				retrieveDefaultRating()));
		refreshAdapter();
	}

	protected void updateQuoteRatingList(int quoteID, Drawable image) {
		ratedQuotesVOList.get(quoteID).setImage(image);
		refreshAdapter();
	}

	protected Drawable retrieveDefaultRating() {
		return this.getResources().getDrawable(R.drawable.notrated);
	}

	protected String retrieveNewQuote() {
		String newQuote = newQuoteTextID.getText().toString();
		return newQuote;
	}

	public void refreshAdapter() {
		((BaseAdapter) getListAdapter()).notifyDataSetChanged();
	}

	public ArrayList<QuoteRatingVO> getRatedQuotesVOList() {
		return ratedQuotesVOList;
	}
}
