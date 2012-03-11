package com.actionbarsherlock.sample.shakespeare.notes;

import java.util.Map;

import android.util.Log;

/*
 * This class is responsible for providing Titles/Details to NotesDAO by
 * delegating the file read to NotesReader and delegating formatting of file
 * data(from String to Map) to NotesFormatter.
 * This class is also responsible for persisting Titles/Details from NotesDAO by
 * delegating formatting of Notes data (from Map to String) to NotesFormatter
 * and delegating the file write to NotesWriter.
 */

class NotesProcessor {
	private static final String TAG = "NotesProcessor";
	
	static NotesProcessor thisInstance;
	
	// private final NotesWriter writer;
	private final NotesFormatter formatter;
	
	private NotesProcessor() {
		// writer = NotesWriter.getInstance();
		formatter = NotesFormatter.getInstance();
	}
	
	static NotesProcessor getInstance() {
		if (thisInstance == null) {
			thisInstance = new NotesProcessor();
		}
		return thisInstance;
	}
	
	void persistNotes(Map<String, String> notes) {
		String strNotes = formatter.formatToString(notes);
		Log.d(TAG, "formatted notes to persist are " + strNotes);
		// writer.persist(strNotes);
	}
}
