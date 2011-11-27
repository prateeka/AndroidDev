package com.androidcourse.hw4.listeners.result;

import android.content.Intent;

public interface ActivityResultListener {
	public void processResult(int requestCode, int resultCode, Intent data);
}
