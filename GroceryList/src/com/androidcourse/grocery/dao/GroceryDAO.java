package com.androidcourse.grocery.dao;

import android.content.ContentValues;
import android.database.Cursor;

public interface GroceryDAO {
	
	Cursor getTraderCursor();
	
	Cursor getItemCursor(String selection,
			String[] selectionArgs);
	
	int updateItem(ContentValues updateValues, String whereClause);
	
	long addItem(ContentValues values);
	
	int deleteItem(long itemid);
	
}
