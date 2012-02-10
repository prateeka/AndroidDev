package com.arya.browser.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplaySourceOrCookieActivity extends Activity {
	private TextView display;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaysourcecookie);
		initViews();
	}
	
	protected void initViews() {
		display = (TextView) findViewById(R.id.textView1);
	}
}
