package com.arya.browser.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import com.arya.browser.http.HelloWebViewClient;

public class BrowserAppActivity extends Activity {
	private final String DEFAULT_URL = "http://www.usf.edu";
	private final int TOAST_DURATION = 10;
	private final int ID_DISPLAY_SOURCE_COOKIE = 1;
	
	WebView mWebView;
	EditText urlText;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initViews();
		setWebViewConfig();
		populateWebView(DEFAULT_URL);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.browsermenu, menu);
		return true;
	}
	
	protected void setWebViewConfig() {
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new HelloWebViewClient());
	}
	
	protected void initViews() {
		urlText = (EditText) findViewById(R.id.editText1);
		mWebView = (WebView) findViewById(R.id.webview);
	}
	
	protected void populateWebView(String url) {
		mWebView.loadUrl(url);
	}
	
	public void goToLink(View view) {
		String url = urlText.getText().toString();
		populateWebView(url);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		boolean handled = false;
		Toast.makeText(
				this,
				"onOptionsItemSelected selected ",
				TOAST_DURATION)
				.show();
		if (item.getItemId() == R.id.menu_ViewSource) {
			Intent intent = new Intent(
					this,
					DisplaySourceOrCookieActivity.class);
			addDataToIntent(
					intent,
					getResources().getString(R.string.displaySource));
			this.startActivityForResult(intent, ID_DISPLAY_SOURCE_COOKIE);
			handled = true;
		}
		return handled;
	}
	
	private void addDataToIntent(Intent intent, String action) {
		intent.putExtra(getResources().getString(R.string.actionType),
				action);
	}
}