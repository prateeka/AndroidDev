package com.actionbarsherlock.sample.shakespeare.notes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class Notes {
	private static final String TAG = "Notes";
	
	private static Notes thisInstance;
	private static Map<String, String> notes;
	private static List<String> titles;
	
	public static Notes getInstance() {
		if (thisInstance == null) {
			thisInstance = new Notes();
		}
		return thisInstance;
	}
	
	static {
		populateNotes();
		populateTitles();
	}
	
	private static void populateNotes() {
		notes = new HashMap<String, String>();
		notes.put("key1", "value1");
		notes.put("key2", "value2");
		notes.put("key3", "value3");
		notes.put("key4", "value4");
		notes.put("key5", "value5");
		notes.put("key6", "value6");
		notes.put("key7", "value7");
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
	
	synchronized List<String> getTitles() {
		return titles;
	}
	
	String getDetail(String titleSelected) {
		return notes.get(titleSelected);
	}
	
	void saveNote(String title, String detail) {
		notes.put(title, detail);
		populateTitles();
	}
	
	String updateNote(String titlePrevSelected, String updatedTitle,
			String updatedDetail) {
		notes.remove(titlePrevSelected);
		String tmp = notes.put(updatedTitle, updatedDetail);
		populateTitles();
		return tmp;
	}
	
	String deleteNote(String titlePrevSelected) {
		String tmp = notes.remove(titlePrevSelected);
		populateTitles();
		return tmp;
	}
	
	private Notes() {
	}
}
