package com.arya.browser.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class DisplaySourceOrCookieActivity extends Activity {
	String url;
	String action;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaysourcecookie);
		populateTextView();
	}
	
	private void populateTextView() {
		String[] params = retrieveActionAndURL();
		HTTPTask task = new HTTPTask(this);
		task.execute(params);
	}
	
	private String[] retrieveActionAndURL() {
		String[] strArray = new String[2];
		Intent intent = getIntent();
		strArray[0] = intent.getStringExtra(getResources().getString(
				R.string.actionType));
		strArray[1] = intent.getStringExtra(getResources().getString(
				R.string.url));
		
		return strArray;
	}
}
