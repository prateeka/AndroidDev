package com.androidcourse.hw2.handler;

import android.app.Activity;
import android.content.Intent;

public interface IOnActivityResultHandler {

	public abstract void onActivityResult(Activity activity, int requestCode,
			int resultCode, Intent data);

}