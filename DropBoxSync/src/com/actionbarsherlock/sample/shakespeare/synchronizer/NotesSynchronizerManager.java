package com.actionbarsherlock.sample.shakespeare.synchronizer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.util.Log;

import com.actionbarsherlock.sample.shakespeare.notes.Notes;

public class NotesSynchronizerManager {
	private static final String TAG = "NotesSynchronizerManager";
	static NotesSynchronizerManager thisInstance;
	
	private ExecutorService executor;
	
	private NotesSynchronizerManager() {
	}
	
	public static NotesSynchronizerManager getInstance() {
		if (thisInstance == null) {
			thisInstance = new NotesSynchronizerManager();
		}
		return thisInstance;
	}
	
	public void startSynchronizing(Notes notes) {
		NotesSynchronizer synchronizer = NotesSynchronizer.getInstance(notes);
		executor = Executors.newSingleThreadExecutor();
		executor.execute(synchronizer);
	}
	
	public void endSynchronizing() {
		executor.shutdownNow();
		final int timeout = 30;
		try {
			executor.awaitTermination(timeout, TimeUnit.SECONDS);
		}
		catch (InterruptedException ex) {
			Log.d(TAG, "AwaitTermination interrupted " + ex);
		}
	}
}
