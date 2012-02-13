package com.arya.browser.activity;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/*
 * This class enables any new link clicked inside the webview to be opened
 * within the webview itself instead of fwding the request to the browser.
 */
public class HelloWebViewClient extends WebViewClient {
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return true;
	}
}
