package com.androidcourse.grocery.dao;

import android.content.ContentValues;
import android.database.Cursor;

public interface GroceryDAO {
	
	Cursor getTraderCursor();
	
	Cursor getItemCursor(String selection,
			String[] selectionArgs);
	
	int updateItem(long itemId, String text, float parseFloat, String note,
			long traderId);
	
	long addItem(ContentValues values);
	
	int deleteItem(long itemid);
	
}
