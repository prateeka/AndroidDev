package com.androidcourse.hw3.activity.displayQuote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.androidcourse.hw3.R;
import com.androidcourse.hw3.vo.QuoteRatingVO;

class RatedQuotePersistAction {
	DisplayQuoteActivity activity;
	private final String prefsFileName = "PrefFile_Prateek_HW3";

	RatedQuotePersistAction(DisplayQuoteActivity paramActivity) {
		activity = paramActivity;
	}

	ArrayList<QuoteRatingVO> getRatedQuotesList() {
		ArrayList<QuoteRatingVO> ratedQuotesList = new ArrayList<QuoteRatingVO>();

		List<String> quotesList = new ArrayList<String>(
				Arrays.asList(activity.getResources().getStringArray(
						R.array.quotes)));

		for (String quote : quotesList) {
			QuoteRatingVO quoteRatingMapping = new QuoteRatingVO(
					quote, activity.getResources()
							.getDrawable(R.drawable.notrated));
			ratedQuotesList.add(quoteRatingMapping);
		}

		return ratedQuotesList;
	}

}
