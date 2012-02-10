package com.arya.browser.http;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HelloWebViewClient extends WebViewClient {
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		System.out.println("inside HelloWebViewClient");
		view.loadUrl(url);
		return true;
	}
}
