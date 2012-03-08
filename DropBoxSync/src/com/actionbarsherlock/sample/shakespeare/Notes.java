package com.actionbarsherlock.sample.shakespeare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

public final class Notes {
	private static Map<String, String> notes = new HashMap<String, String>();
	private static List<String> titles;
	private static Notes thisInstance;
	
	private static final String TAG = "Notes";
	
	static {
		populateNotes();
		populateTitles();
	}
	
	private static void populateNotes() {
		notes.put("key1", "value1");
		notes.put("key2", "value2");
		notes.put("key3", "value3");
		notes.put("key4", "value4");
		notes.put("key5", "value5");
		notes.put("key6", "value6");
		notes.put("key7", "value7");
	}
	
	public static Notes getInstance() {
		if (thisInstance == null) {
			thisInstance = new Notes();
		}
		return thisInstance;
	}
	
	private Notes() {
	}
	
	private static void populateTitles() {
		titles = new ArrayList<String>(notes.keySet());
		
		// Below printing is for debugging only
		StringBuilder tmpTitles = new StringBuilder();
		for (String title : titles) {
			tmpTitles.append(title);
		}
		Log.d(TAG, "titles available are: " + tmpTitles);
	}
	
	public synchronized List<String> getTitles() {
		return titles;
	}
	
	public synchronized String getDetail(String titleSelected) {
		return notes.get(titleSelected);
	}
	
	/*-	public synchronized String getTitle(int position) {
	 return titles[position];
	 }*/
	
	public synchronized void saveNote(String title, String detail) {
		notes.put(title, detail);
		populateTitles();
	}
	
	public void updateNote(String titlePrevSelected, String updatedTitle,
			String updatedDetail) {
		notes.remove(titlePrevSelected);
		notes.put(updatedTitle, updatedDetail);
		populateTitles();
	}
}
