package com.androidcourse.hw3.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.androidcourse.hw3.R;

public class DrawableImageMapper {
	public Drawable retrieveDrawableImage(Context context, int index) {
		switch (index) {
		case 0:
			return context.getResources().getDrawable(
					R.drawable.notrated);
		case 1:
			return context.getResources().getDrawable(
					R.drawable.poor);
		case 2:
			return context.getResources().getDrawable(
					R.drawable.fair);
		case 3:
			return context.getResources().getDrawable(
					R.drawable.good);
		default:
			return null;
		}
	}
}
