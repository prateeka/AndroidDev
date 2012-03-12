package com.actionbarsherlock.sample.shakespeare.synchronizer;

import java.util.Calendar;

import android.util.Log;

import com.actionbarsherlock.sample.shakespeare.notes.Notes;

/*
 * Synchronizes NotesStore with an email service at a constant time interval.
 * This uses threads to synchronize at a constant time interval.
 */
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
			mail.setBody(notes.toString());
			mail.send();
			Log.d(
					TAG,
					"Email sent @ "
							+ java.text.DateFormat
									.getDateTimeInstance()
									.format(Calendar.getInstance().getTime()));
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			Log.e(TAG, ex.toString());
		}
	}
}
