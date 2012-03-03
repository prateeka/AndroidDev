package com.actionbarsherlock.sample.shakespeare.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.sample.shakespeare.Shakespeare;

public class DetailsFragment extends Fragment {
	private static final String TAG = "DetailsFragment";
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState == null) {
			Log.d(TAG, "onSaveInstanceState called with empty outState");
		} else {
			Log.d(
					TAG,
					"onSaveInstanceState called with : " + outState.toString());
		}
		super.onSaveInstanceState(outState);
		Log.d(
				TAG,
				"onSaveInstanceState with bundle after saving: "
						+ outState.toString());
	}
	
	@Override
	public void onPause() {
		Log.d(TAG, "onPause called");
		super.onPause();
	}
	
	@Override
	public void onStop() {
		Log.d(TAG, "onStop called");
		super.onStop();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			Log.d(TAG, "onCreate called with empty savedInstanceState");
		} else {
			Log.d(
					TAG,
					"onCreate called with : " + savedInstanceState.toString());
		}
		super.onCreate(savedInstanceState);
	}
	
	/**
	 * Create a new instance of DetailsFragment, initialized to
	 * show the text at 'index'.
	 */
	public static DetailsFragment newInstance(int index) {
		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		
		DetailsFragment f = new DetailsFragment();
		f.setArguments(args);
		
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			// We have different layouts, and in one of them this
			// fragment's containing frame doesn't exist. The fragment
			// may still be created from its saved state, but there is
			// no reason to try to create its view hierarchy because it
			// won't be displayed. Note this is not needed -- we could
			// just run the code below, where we would create and return
			// the view hierarchy; it would just never be used.
			return null;
		}
		
		if (savedInstanceState == null) {
			Log.d(TAG, "onCreateView called with empty savedInstanceState");
		} else {
			Log.d(
					TAG,
					"onCreateView called with : "
							+ savedInstanceState.toString());
		}
		
		ScrollView scroller = new ScrollView(getActivity());
		TextView text = new TextView(getActivity());
		int padding = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP,
				4,
				getActivity().getResources().getDisplayMetrics());
		text.setPadding(padding, padding, padding, padding);
		scroller.addView(text);
		text.setText(Shakespeare.DIALOGUE[getArguments().getInt("index", 0)]);
		return scroller;
	}
}
