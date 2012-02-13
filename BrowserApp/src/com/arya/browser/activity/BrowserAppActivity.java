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


/*
 * This is the activity displaying the webview for a URL.
 * When the menu is selected for either displaying source or cookie, new
 * activity "DisplaySourceOrCookieActivity" is started.
 */

public class BrowserAppActivity extends Activity {
	private final String DEFAULT_URL = "http://www.usf.edu";
	private final int TOAST_DURATION = 10;
	private final int ID_DISPLAY_SOURCE_COOKIE = 1;
	
	private WebView mWebView;
	private EditText urlText;
	
	private String url;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initViews();
		setWebViewConfig();
		url = DEFAULT_URL;
		populateWebView(url);
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
		url = urlText.getText().toString();
		populateWebView(url);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		boolean handled = false;
		/*-		Toast.makeText(
		 this,
		 "onOptionsItemSelected selected ",
		 TOAST_DURATION)
		 .show();
		 */if (item.getItemId() == R.id.menu_ViewSource) {
			Intent intent = getIntentForNextActivity(DisplaySourceOrCookieActivity.class);
			addActionAndURLToIntent(
					intent,
					getResources().getString(R.string.displaySource));
			this.startActivityForResult(intent, ID_DISPLAY_SOURCE_COOKIE);
			handled = true;
		} else if (item.getItemId() == R.id.menu_ViewCookie) {
			Intent intent = getIntentForNextActivity(DisplaySourceOrCookieActivity.class);
			addActionAndURLToIntent(
					intent,
					getResources().getString(R.string.displayCookie));
			this.startActivityForResult(intent, ID_DISPLAY_SOURCE_COOKIE);
			handled = true;
		}
		return handled;
	}
	
	protected Intent getIntentForNextActivity(
			Class<DisplaySourceOrCookieActivity> classActivity) {
		return new Intent(
				this,
				classActivity);
	}
	
	private void addActionAndURLToIntent(Intent intent, String action) {
		intent.putExtra(getResources().getString(R.string.actionType),
				action);
		intent.putExtra(getResources().getString(R.string.url),
				url);
	}
}