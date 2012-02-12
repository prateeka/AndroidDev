package com.arya.androidcourse.service.http;

import java.util.HashMap;
import java.util.Map;

class URLLockProvider {
	
	private static URLLockProvider thisInstance;
	private final Map<String, String> urlLocksMap;
	
	static URLLockProvider getInstance() {
		if (thisInstance == null) {
			thisInstance = new URLLockProvider();
		}
		return thisInstance;
	}
	
	private URLLockProvider() {
		urlLocksMap = new HashMap<String, String>();
	}
	
	String retrieveLock(String url) {
		String urlLock = null;
		if (checkIfLockExists(url)) {
			urlLock = retrieveExistingLock(url);
			return urlLock;
		} else {
			urlLock = insertNewLock(url);
			return urlLock;
		}
	}
	
	private String insertNewLock(String url) {
		String urlLock = null;
		synchronized (urlLocksMap) {
			if (checkIfLockExists(url)) {
				urlLock = retrieveExistingLock(url);
			} else {
				urlLock = new String(url);
				urlLocksMap.put(url, urlLock);
			}
		}
		return urlLock;
	}
	
	private String retrieveExistingLock(String url) {
		String urlLock = urlLocksMap.get(url);
		return urlLock;
	}
	
	private boolean checkIfLockExists(String url) {
		boolean flagURLLockExists = urlLocksMap.containsKey(url);
		return flagURLLockExists;
	}
	
}
