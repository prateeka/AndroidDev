package com.actionbarsherlock.sample.shakespeare.notes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.util.Log;

/*
 * This class is responsible for reading/writing Notes from internal storage. It
 * uses Java serialization to persist the data.
 */

class NotesReaderWriter {
	private static final String TAG = "NotesReaderWriter";
	
	static NotesReaderWriter thisInstance;
	private final String FILENAME = "notesStore";
	
	private NotesReaderWriter() {
	}
	
	static NotesReaderWriter getInstance() {
		if (thisInstance == null) {
			thisInstance = new NotesReaderWriter();
		}
		return thisInstance;
	}
	
	void persistNotes(Context context, Object obj) {
		serializeNotes(context, obj);
	}
	
	Object readNotes(Context context) {
		return deserializeNotes(context);
	}
	
	void serializeNotes(Context context, Object o) {
		FileOutputStream fos;
		try {
			fos = context.openFileOutput(
					FILENAME,
					Context.MODE_PRIVATE);
			ObjectOutput out = new ObjectOutputStream(fos);
			out.writeObject(o);
			out.close();
			Log.i(TAG, "notes serialized to " + FILENAME);
		}
		catch (FileNotFoundException ex) {
			Log.e(TAG, "file not found while serializing notes", ex);
		}
		catch (IOException ioe) {
			Log.e(TAG, "error while serializing notes", ioe);
		}
	}
	
	public Object deserializeNotes(Context context) {
		Object obj = null;
		FileInputStream fis;
		try {
			fis = context.openFileInput(FILENAME);
			ObjectInput in = new ObjectInputStream(fis);
			obj = in.readObject();
			Log.d(TAG, "return from deserializeNotes");
			in.close();
		}
		catch (FileNotFoundException ex) {
			Log.e(TAG, "file not found while deserializing notes ", ex);
		}
		catch (IOException ioe) {
			Log.e(TAG, "error while deserializing notes", ioe);
		}
		catch (ClassNotFoundException ex) {
			Log.e(TAG, "error while deserializing notes", ex);
		}
		
		return obj;
	}
}
