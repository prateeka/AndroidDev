package com.arya.androidcourse.service.http;

import java.util.HashMap;
import java.util.Map;

/*
 * This class plays the role of a cache. For every request to download content
 * (either Text or Image), it first checks up its cache (in the form of Map) to
 * determine if the content exists. If it exists, it serves response from the
 * cache. If the content does not exist in cache, then it obtains a lock from
 * URLLockProvider and then makes a request to download the content(either text
 * or image), populates its cache with the response and then releases the lock.
 */

public class HttpProcessorDecorator extends HttpProcessor {
	private static HttpProcessorDecorator thisInstance;
	
	private final Map<String, String> textContentMap;
	
	private final URLLockProvider lockProvider;
	
	static HttpProcessorDecorator getInstance() {
		if (thisInstance == null) {
			thisInstance = new HttpProcessorDecorator();
		}
		return thisInstance;
	}
	
	private HttpProcessorDecorator() {
		textContentMap = new HashMap<String, String>();
		lockProvider = URLLockProvider.getInstance();
	}
	
	@Override
	protected String getTextContent(String url) {
		String textContent = null;
		boolean isTextContentExists = checkIfTextContentExists(url);
		
		if (isTextContentExists) {
			textContent = retrieveTextContent(url);
		} else {
			textContent = insertNewTextContent(url);
		}
		return textContent;
	}
	
	@Override
	protected ParseableByteArray getImageContent(String url) {
		return super.getImageContent(url);
	}
	
	private String insertNewTextContent(String url) {
		String textContent = null;
		String urlLock = lockProvider.retrieveLock(url);
		
		synchronized (urlLock) {
			if (checkIfTextContentExists(urlLock)) {
				textContent = retrieveTextContent(url);
			}
			else {
				textContent = super.getTextContent(url);
				textContentMap.put(url, textContent);
			}
		}
		return textContent;
	}
	
	private String retrieveTextContent(String url) {
		String urlTextContent = textContentMap.get(url);
		return urlTextContent;
	}
	
	private boolean checkIfTextContentExists(String url) {
		boolean flagTextContentExists = textContentMap.containsKey(url);
		return flagTextContentExists;
	}
	
}
