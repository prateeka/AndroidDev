package com.actionbarsherlock.sample.shakespeare.notes;

import java.util.List;

import android.content.Context;
import android.util.Log;

/*
 * Exposes functions to manipulate Notes Store (represented by class:Notes)
 */
public final class NotesDAO {
	
	private static NotesDAO thisInstance;
	
	private static final String TAG = "NotesDAO";
	private final Notes notes;
	private final NotesProcessor processor;
	private final Context context;
	
	// The below is synchronized such that if both TitleFragment and
	// DetailFragment try to access this at the same time through different
	// threads then only one should be able to get the monitor.
	public synchronized static NotesDAO getInstance(Context context) {
		if (thisInstance == null) {
			thisInstance = new NotesDAO(context);
		}
		return thisInstance;
	}
	
	private NotesDAO(Context pContext) {
		context = pContext;
		processor = NotesProcessor.getInstance();
		notes = processor.readNotes(context);
		Log.d(TAG, "initial notes are " + notes);
	}
	
	public List<String> getTitles() {
		return notes.getTitles();
	}
	
	public String getDetail(String titleSelected) {
		return notes.getDetail(titleSelected);
	}
	
	public void saveNote(String title, String detail) {
		notes.saveNote(title, detail);
	}
	
	public String updateNote(String titlePrevSelected, String updatedTitle,
			String updatedDetail) {
		String tmp = notes.updateNote(titlePrevSelected, updatedTitle,
				updatedDetail);
		return tmp;
	}
	
	public String deleteNote(String titlePrevSelected) {
		String tmp = notes.deleteNote(titlePrevSelected);
		return tmp;
	}
	
	public void persist() {
		processor.persistNotes(context, notes);
	}
	
	/*
	 * Method used to inform the DAO that the fragment has resumed. Accordingly,
	 * DAO can inform the processor to start synchronizing.
	 * This was introduced as when Home was pressed and later this application
	 * was again restored, fragment startup method onActivityCreated was not
	 * called but onResume was called.
	 */
	public void onFragmentResume() {
		processor.startSynchronizing(notes);
	}
}
