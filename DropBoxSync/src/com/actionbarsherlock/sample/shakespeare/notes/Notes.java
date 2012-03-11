package com.actionbarsherlock.sample.shakespeare.notes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class Notes implements Serializable {
	
	private static final long serialVersionUID = 4272774288661526442L;
	
	private static final String TAG = "Notes";
	
	private final Map<String, String> notes;
	private List<String> titles;
	
	Notes(Map<String, String> pnotes) {
		if (pnotes == null) {
			pnotes = new HashMap<String, String>();
		}
		notes = pnotes;
		populateTitles();
	}
	
	/*-
	private static Notes thisInstance;
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
	 */
	
	Map<String, String> getNotes() {
		return notes;
	}
	
	List<String> getTitles() {
		
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
	
	private void populateTitles() {
		titles = new ArrayList<String>(notes.keySet());
		
		// Below printing is for debugging only
		StringBuilder tmpTitles = new StringBuilder();
		for (String title : titles) {
			tmpTitles.append(title);
		}
		Log.d(TAG, "titles available are: " + tmpTitles);
	}
	
	@Override
	public String toString() {
		return "Notes [" + notes + "]";
	}
}
