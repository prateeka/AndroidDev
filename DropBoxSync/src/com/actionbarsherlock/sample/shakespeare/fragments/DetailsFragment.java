package com.actionbarsherlock.sample.shakespeare.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.sample.shakespeare.Notes;
import com.actionbarsherlock.sample.shakespeare.R;
import com.actionbarsherlock.sample.shakespeare.activities.TitlesActivity;

public class DetailsFragment extends Fragment {
	private static DetailsFragment thisInstance;
	private EditText titleView;
	private EditText detailView;
	private Button save;
	private Button cancel;
	private ButtonOnClickListener listener;
	private Notes notes;
	private TitlesActivity activity;
	private String titlePrevSelected;
	
	private static final String TAG = "DetailsFragment";
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		thisInstance = this;
		listener = new ButtonOnClickListener();
		notes = Notes.getInstance();
		activity = (TitlesActivity) getActivity();
		initViews();
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
	
	public void displayDetails(String titleSelected) {
		Log.d(TAG, "titleSelected is " + titleSelected);
		titlePrevSelected = titleSelected;
		populateTitleAndDetail(titleSelected);
		showTextViews();
		disableTextViews();
	}
	
	public void addNote() {
		resetTitlePrevSelected();
		populateTitleAndDetail();
		enableTextViews();
		showButtons();
	}
	
	public void updateNote() {
		Log.d(TAG, "updateNote selected ");
		enableTextViews();
		showButtons();
	}
	
	private void populateTitleAndDetail(String titleSelected) {
		titleView.setText(titleSelected);
		detailView.setText(notes.getDetail(titleSelected));
	}
	
	private void populateTitleAndDetail() {
		titleView.setText("");
		detailView.setText("");
	}
	
	private void enableTextViews() {
		showTextViews();
		titleView.setEnabled(true);
		detailView.setEnabled(true);
	}
	
	private void showTextViews() {
		titleView.setVisibility(View.VISIBLE);
		detailView.setVisibility(View.VISIBLE);
	}
	
	private void disableTextViews() {
		titleView.setEnabled(false);
		detailView.setEnabled(false);
		hideButtons();
	}
	
	private void showButtons() {
		save.setVisibility(View.VISIBLE);
		cancel.setVisibility(View.VISIBLE);
	}
	
	private void hideButtons() {
		save.setVisibility(View.INVISIBLE);
		cancel.setVisibility(View.INVISIBLE);
	}
	
	private void hideViews() {
		titleView.setVisibility(View.INVISIBLE);
		detailView.setVisibility(View.INVISIBLE);
		hideButtons();
	}
	
	private View findViewById(int id) {
		return getActivity().findViewById(id);
	}
	
	private void initViews() {
		titleView = (EditText) findViewById(
				R.id.title);
		detailView = (EditText) findViewById(
				R.id.detail);
		save = (Button) findViewById(R.id.save);
		cancel = (Button) findViewById(R.id.cancel);
		save.setOnClickListener(listener);
	}
	
	private void refreshTitles() {
		activity.refreshTitles();
	}
	
	private void resetTitlePrevSelected() {
		titlePrevSelected = null;
	}
	
	private class ButtonOnClickListener implements OnClickListener {
		
		private final String TAG = "ButtonOnClickListener";
		
		@Override
		public void onClick(View v) {
			if (v.getId() == save.getId()) {
				Log.d(TAG, "onClick called for save button");
				Log.d(TAG, "title value and detail value : "
						+ titleView.getText().toString() + ":"
						+ detailView.getText().toString());
				if (titlePrevSelected == null) {
					notes.saveNote(titleView.getText().toString(),
							detailView.getText().toString());
				} else {
					notes.updateNote(titlePrevSelected,
							titleView.getText().toString(),
							detailView.getText().toString());
				}
			}
			
			resetTitlePrevSelected();
			hideViews();
			refreshTitles();
		}
		
	}
}
