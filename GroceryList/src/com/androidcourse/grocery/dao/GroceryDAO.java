package com.androidcourse.grocery.dao;

import android.database.Cursor;

public interface GroceryDAO {
	
	Cursor getTraderCursor();
	
	Cursor getItemCursor(String selection,
			String[] selectionArgs);
	
	Cursor getItemCursorForItemId(long itemId);
	
	int updateItem(long itemId, String text, float parseFloat, String note,
			long traderId);
	
	long addItem(String text, float parseFloat, String note, long traderId);
	
	int deleteItem(long itemid);
	
}
