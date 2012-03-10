package com.actionbarsherlock.sample.shakespeare.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.SupportActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.sample.shakespeare.Notes;

public class TitlesFragment extends ListFragment {
	int mPositionChecked = 0;
	int mPositionShown = -1;
	private OnArticleSelectedListener mListener;
	private Notes notes;
	private static TitlesFragment thisInstance;
	private ArrayAdapter<String> titleAdapter;
	
	private static final String TAG = "TitlesFragment";
	
	public static TitlesFragment getInstance() {
		return thisInstance;
	}
	
	@Override
	public void onAttach(SupportActivity activity) {
		super.onAttach(activity);
		Log.d(TAG, "onAttach called");
		try {
			mListener = (OnArticleSelectedListener) activity;
		}
		catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnArticleSelectedListener");
		}
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "onActivityCreated called");
		notes = Notes.getInstance();
		thisInstance = this;
		getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
		// Populate list with array of titles.
		titleAdapter = new ArrayAdapter<String>(
				getActivity(),
				android.R.layout.simple_list_item_1,
				notes.getTitles());
		setListAdapter(titleAdapter);
		
		/*-
		ToDo: Check how this needs to be restored
		// Restore last state for checked position.
		if (savedInstanceState != null) {
			mPositionChecked = savedInstanceState.getInt("curChoice", 0);
			mPositionShown = savedInstanceState.getInt("shownChoice", -1);
		}*/
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		mPositionChecked = position;
		if (mPositionShown != mPositionChecked) {
			mPositionShown = position;
			String titleSelected = titleAdapter.getItem(position);
			showDetails(titleSelected);
		} else {
			// For debugging only
			Log.d(TAG, "prevPosition and currentPosition selected match "
					+ mPositionShown + ":" + mPositionChecked);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putInt("curChoice", mPositionChecked);
		outState.putInt("shownChoice", mPositionShown);
	}
	
	// Helper function to show the details of a selected item
	void showDetails(String titleSelected) {
		mListener.onArticleSelected(titleSelected);
	}
	
	public void refreshTitles() {
		Log.d(TAG, "refreshTitle called");
		resetTitlePositionSelected();
		// ToDo: replace it with DataSetObserver
		titleAdapter = new ArrayAdapter<String>(
				getActivity(),
				android.R.layout.simple_list_item_1,
				notes.getTitles());
		setListAdapter(titleAdapter);
	}
	
	public void resetTitlePositionSelected() {
		mPositionShown = -1;
	}
}
