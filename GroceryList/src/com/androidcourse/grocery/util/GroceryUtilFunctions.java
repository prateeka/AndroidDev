package com.androidcourse.grocery.util;

import android.content.Intent;

public class GroceryUtilFunctions {
	public static long getIntentData(Intent intent, String key) {
		return intent.getExtras().getLong(key);
	}

}
