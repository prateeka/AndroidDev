package com.arya.androidcourse.service.http.processor;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.arya.androidcourse.service.http.ParseableByteArray;

/*
 * This class plays the role of a cache. For every request to download content
 * (either Text or Image), it first checks up its cache (in the form of Map) to
 * determine if the content exists. If it exists, it serves response from the
 * cache. If the content does not exist in cache, then it obtains a lock from
 * URLLockProvider and then makes a request to download the content(either text
 * or image), populates its cache with the response and then releases the lock.
 */

public class HttpProcessorCache extends HttpProcessor {
	private final String TAG = "HttpProcessorCache";
	
	private static HttpProcessorCache thisInstance;
	private final Map<String, String> textContentMap;
	private final URLLockProvider lockProvider;
	
	public static HttpProcessorCache getInstance() {
		if (thisInstance == null) {
			thisInstance = new HttpProcessorCache();
		}
		return thisInstance;
	}
	
	private HttpProcessorCache() {
		textContentMap = new HashMap<String, String>();
		lockProvider = URLLockProvider.getInstance();
	}
	
	@Override
	public String getTextContent(String url) {
		String textContent = null;
		boolean isTextContentExists = checkIfTextContentExists(url);
		
		if (isTextContentExists) {
			Log.i(TAG, "TextContent cache hit for url :" + url);
			textContent = retrieveExistingTextContent(url);
		} else {
			Log.i(TAG, "TextContent cache miss for url :" + url);
			textContent = insertNewTextContent(url);
		}
		return textContent;
	}
	
	@Override
	public ParseableByteArray getImageContent(String url) {
		return super.getImageContent(url);
	}
	
	private String insertNewTextContent(String url) {
		String textContent = null;
		String urlLock = lockProvider.getLock(url);
		
		synchronized (urlLock) {
			if (checkIfTextContentExists(urlLock)) {
				textContent = retrieveExistingTextContent(url);
			}
			else {
				textContent = super.getTextContent(url);
				textContentMap.put(url, textContent);
			}
		}
		return textContent;
	}
	
	private String retrieveExistingTextContent(String url) {
		String urlTextContent = textContentMap.get(url);
		return urlTextContent;
	}
	
	private boolean checkIfTextContentExists(String url) {
		boolean flagTextContentExists = textContentMap.containsKey(url);
		return flagTextContentExists;
	}
	
}
