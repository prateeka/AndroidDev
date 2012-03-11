package com.actionbarsherlock.sample.shakespeare.synchronizer;

import android.util.Log;

import com.actionbarsherlock.sample.shakespeare.notes.Notes;

class NotesSynchronizer implements Runnable {
	
	private static final String TAG = "NotesSynchronizer";
	static NotesSynchronizer thisInstance;
	private final Notes notes;
	
	private NotesSynchronizer(Notes pnotes) {
		notes = pnotes;
	}
	
	static NotesSynchronizer getInstance(Notes pnotes) {
		if (thisInstance == null) {
			thisInstance = new NotesSynchronizer(pnotes);
		}
		return thisInstance;
	}
	
	@Override
	public void run() {
		final int sleepTime = 100000;
		Log.d(TAG, "Thread starts execution : "
				+ Thread.currentThread().getName());
		while (!Thread.currentThread().isInterrupted()) {
			sendEmail();
			try {
				Thread.sleep(sleepTime);
			}
			catch (InterruptedException e) {
				sendEmail();
				Log.d(TAG, "NotesSynchronizer thread interrupted " + e);
				break;
			}
		}
		
		Log.d(TAG, "Thread ends execution : "
				+ Thread.currentThread().getName());
	}
	
	private void sendEmail() {
		Mail mail = new Mail();
		try {
			mail.send();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			Log.e(TAG, ex.toString());
		}
	}
}
