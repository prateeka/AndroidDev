package com.actionbarsherlock.sample.shakespeare.notes;

import java.util.Map;
import java.util.Map.Entry;

public class NotesFormatter {
	private static final String TAG = "NotesFormatter";
	
	private final String DELIMITER = "|";
	private final String NOTE_SEPERATOR = "\n";
	
	static NotesFormatter thisInstance;
	
	static NotesFormatter getInstance() {
		if (thisInstance == null) {
			thisInstance = new NotesFormatter();
		}
		return thisInstance;
	}
	
	String formatToString(Map<String, String> notes) {
		StringBuilder unmarshalledNotes = new StringBuilder();
		
		for (Entry<String, String> note : notes.entrySet()) {
			String key = note.getKey();
			String value = note.getValue();
			unmarshalledNotes.append(key + DELIMITER + value + NOTE_SEPERATOR);
		}
		return null;
	}
}
