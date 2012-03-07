package com.actionbarsherlock.sample.shakespeare.fragments;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ButtonOnClickListener_1 implements OnClickListener {
	
	private static final String TAG = "ButtonOnClickListener";
	private static ButtonOnClickListener_1 thisInstance;
	private EditText titleView;
	private EditText contentView;
	private Button save;
	private Button cancel;
	
	public static ButtonOnClickListener_1 getInstance(EditText titleView,
			EditText contentView,
			Button save,
			Button cancel) {
		if (thisInstance == null) {
			thisInstance = new ButtonOnClickListener_1();
		}
		thisInstance.titleView = titleView;
		thisInstance.contentView = contentView;
		thisInstance.save = save;
		thisInstance.cancel = cancel;
		return thisInstance;
	}
	
	@Override
	public void onClick(View v) {
		Log.d(TAG, "save is " + save);
		
		if (v.getId() == save.getId()) {
			Log.d(TAG, "onClick called for save button");
		}
	}
	
	private ButtonOnClickListener_1() {
	}
	
}
