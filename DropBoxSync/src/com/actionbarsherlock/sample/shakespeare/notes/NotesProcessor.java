package com.actionbarsherlock.sample.shakespeare.notes;

import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.actionbarsherlock.sample.shakespeare.synchronizer.NotesSynchronizerManager;

/*
 * This class is responsible for providing/storing Titles and Details by
 * delegating the file read/write to NotesReaderWriter.
 * This class is also responsible for initiating and ending notes
 * synchronization.
 */

class NotesProcessor {
	private static final String TAG = "NotesProcessor";
	
	static NotesProcessor thisInstance;
	
	private final NotesReaderWriter readerWriter;
	private final NotesSynchronizerManager synchronizerMgr;
	
	private NotesProcessor() {
		readerWriter = NotesReaderWriter.getInstance();
		synchronizerMgr = NotesSynchronizerManager.getInstance();
	}
	
	static NotesProcessor getInstance() {
		if (thisInstance == null) {
			thisInstance = new NotesProcessor();
		}
		return thisInstance;
	}
	
	void persistNotes(Context context, Notes notes) {
		// Log.d(TAG, "notes to persist are " + notes);
		readerWriter.persistNotes(context, notes.getNotes());
		synchronizerMgr.endSynchronizing();
	}
	
	Notes readNotes(Context context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> notesMap = (Map<String, String>) readerWriter
				.readNotes(context);
		Notes notes = new Notes(notesMap);
		Log.i(TAG, "notes deserialized are " + notes);
		
		return notes;
	}
	
	public void startSynchronizing(Notes notes) {
		synchronizerMgr.startSynchronizing(notes);
	}
}
