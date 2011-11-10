package com.androidcourse.hw3.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.androidcourse.hw3.R;
import com.androidcourse.hw3.util.Factory;

public class CustomRatingAdapter extends BaseAdapter {

	private final Activity context;
	private final Integer ratingCount;
	private static final String TAG = "CustomRatingAdapter";

	public CustomRatingAdapter(Activity context, Integer ratingCount) {
		super();
		this.context = context;
		this.ratingCount = ratingCount;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d(TAG, "position called in getView: " + position);
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.ratinglayout,
				null, true);
		ImageView imageView = (ImageView) rowView
				.findViewById(R.id.ratingCaptureIcon);
		imageView.setImageDrawable(Factory.getDrawableImageMapperInstance()
				.retrieveDrawableImage(context, position));
		return rowView;
	}

	@Override
	public int getCount() {
		return ratingCount;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
}
