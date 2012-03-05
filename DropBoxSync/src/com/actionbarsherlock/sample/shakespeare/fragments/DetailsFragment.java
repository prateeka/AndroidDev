package com.actionbarsherlock.sample.shakespeare.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.sample.shakespeare.Notes;
import com.actionbarsherlock.sample.shakespeare.R;

public class DetailsFragment extends Fragment {
	private static final String TAG = "DetailsFragment";
	private static DetailsFragment thisInstance;
	private EditText titleView;
	private EditText contentView;
	private Button save;
	private Button cancel;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		titleView = (EditText) findViewById(
				R.id.title);
		contentView = (EditText) findViewById(
				R.id.content);
		save = (Button) findViewById(R.id.save);
		cancel = (Button) findViewById(R.id.cancel);
	}
	
	public DetailsFragment() {
		thisInstance = this;
	}
	
	public static DetailsFragment getInstance() {
		return thisInstance;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.details, container, false);
		return view;
	}
	
	public void displayDetails(int position) {
		Log.d(TAG, "position received is " + position);
		titleView.setText(Notes.getTitles(position));
		contentView.setText(Notes.getDetails(position));
		disableViews();
		hideButtons();
	}
	
	private void disableViews() {
		titleView.setEnabled(false);
		contentView.setEnabled(false);
	}
	
	private void hideButtons() {
		save.setVisibility(View.INVISIBLE);
		cancel.setVisibility(View.INVISIBLE);
	}
	
	private View findViewById(int id) {
		return getActivity().findViewById(id);
	}
}
