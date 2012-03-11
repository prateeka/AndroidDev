package com.actionbarsherlock.sample.shakespeare.notes;

import java.util.List;

public final class NotesDAO {
	
	private static NotesDAO thisInstance;
	
	// private static final String TAG = "NotesDAO";
	private final Notes notes;
	
	public static NotesDAO getInstance() {
		if (thisInstance == null) {
			thisInstance = new NotesDAO();
		}
		return thisInstance;
	}
	
	private NotesDAO() {
		notes = Notes.getInstance();
	}
	
	public synchronized List<String> getTitles() {
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
}
