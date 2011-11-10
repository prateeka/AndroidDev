package com.androidcourse.hw3.vo;

import android.graphics.drawable.Drawable;

public class QuoteRatingVO {
	private String quote;
	private Drawable image;

	public QuoteRatingVO(String quote, Drawable image) {
		super();
		this.quote = quote;
		this.image = image;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	public Drawable getImage() {
		return image;
	}

	public void setImage(Drawable image) {
		this.image = image;
	}
}
