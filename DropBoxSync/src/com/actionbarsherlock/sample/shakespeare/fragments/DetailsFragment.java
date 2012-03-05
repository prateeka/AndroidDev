package com.actionbarsherlock.sample.shakespeare.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.sample.shakespeare.R;

public class DetailsFragment extends Fragment {
	private static final String TAG = "DetailsFragment";
	private static DetailsFragment thisInstance;
	
	public DetailsFragment() {
		thisInstance = this;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.details, container, false);
		return view;
	}
	
	public void displayDetails(Uri articleUri) {
		Log.d(TAG, "artcleUri received is " + articleUri);
		TextView titleView = (TextView) getActivity().findViewById(
				R.id.title);
		TextView contentView = (TextView) getActivity().findViewById(
				R.id.content);
		
		titleView.setText("tttttttttttttt");
		contentView.setText("ccccc");
	}
	
	/*-@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			Log.d(TAG, "container is null");
			// return null;
		}
		
		View view = inflater.inflate(
				R.layout.details,
				container,
				false);
		if (view != null) {
			TextView titleView = (TextView) getActivity().findViewById(
					R.id.title);
			TextView contentView = (TextView) getActivity().findViewById(
					R.id.content);
			contentView.setText("");
		}
		return view;
		
		}*/
	
	public static DetailsFragment getInstance() {
		return thisInstance;
	}
}
