package com.actionbarsherlock.sample.shakespeare.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItem;
import android.util.Log;

import com.actionbarsherlock.sample.shakespeare.fragments.DetailsFragment;

public class DetailsActivity extends FragmentActivity {
	private static final String TAG = "DetailsActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(TAG, "onCreate called ");
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// If the screen is now in landscape mode, we can show the
			// dialog in-line with the list so we don't need this activity.
			finish();
			return;
		}
		
		if (savedInstanceState == null) {
			// During initial setup, plug in the details fragment.
			DetailsFragment details = new DetailsFragment();
			details.setArguments(getIntent().getExtras());
			
			getSupportFragmentManager()
					.beginTransaction()
					.add(android.R.id.content, details)
					.commit();
		}
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Intent intent = new Intent(this, TitlesActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				// Get rid of the slide-in animation, if possible
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
					// OverridePendingTransition.invoke(this);
				}
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPause() {
		Log.d(TAG, "onPause called");
		super.onPause();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (outState == null) {
			Log.d(TAG, "onSaveInstanceState called with empty bundle");
		} else {
			Log.d(
					TAG,
					"onSaveInstanceState called with : " + outState.toString());
		}
		super.onSaveInstanceState(outState);
		Log.d(
				TAG,
				"onSaveInstanceState called with bundle after saving: "
						+ outState.toString());
	}
	
	@Override
	protected void onResume() {
		Log.d(TAG, "onResume called");
		super.onResume();
	}
	
	@Override
	protected void onStart() {
		Log.d(TAG, "onStart called");
		super.onStart();
	}
	
	@Override
	protected void onRestart() {
		Log.d(TAG, "onRestart called");
		super.onRestart();
	}
	
	@Override
	protected void onStop() {
		Log.d(TAG, "onStop called");
		super.onStop();
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.d(TAG, "onRestoreInstanceState called...");
		
		if (savedInstanceState == null) {
			Log.d(TAG, "onRestoreInstanceState called with empty bundle");
		} else {
			Log.d(
					TAG,
					"onRestoreInstanceState called with : "
							+ savedInstanceState.toString());
		}
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	private static final class OverridePendingTransition {
		static void invoke(Activity activity) {
			activity.overridePendingTransition(0, 0);
		}
	}
}
