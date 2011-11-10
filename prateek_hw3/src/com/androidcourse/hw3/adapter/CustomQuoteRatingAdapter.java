package com.androidcourse.hw3.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidcourse.hw3.R;
import com.androidcourse.hw3.vo.QuoteRatingVO;

public class CustomQuoteRatingAdapter extends BaseAdapter {
	private final Activity context;
	private final ArrayList<QuoteRatingVO> ratedQuotesVOList;
	private static final String TAG = "CustomQuoteRatingAdapter";

	public CustomQuoteRatingAdapter(Activity context,
			ArrayList<QuoteRatingVO> ratedQuotesVOList) {
		super();
		this.context = context;
		this.ratedQuotesVOList = ratedQuotesVOList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.ratedquoteslayout, null, true);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		textView.setText(ratedQuotesVOList.get(position).getQuote());
		imageView.setImageDrawable(ratedQuotesVOList.get(position).getImage());

		return rowView;
	}

	@Override
	public int getCount() {
		return ratedQuotesVOList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}