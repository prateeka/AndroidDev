package com.androidcourse.grocery.dao;

import android.content.ContentValues;
import android.database.Cursor;

/*
 * Lists the operations that can be performed for Trader and ShoppingItem
 * entities.
 */
public interface GroceryDAO {
	
	Cursor getTraderCursor();
	
	Cursor getItemCursor(String selection,
			String[] selectionArgs);
	
	int updateItem(ContentValues updateValues, String whereClause);
	
	long addItem(ContentValues values);
	
	int deleteItem(long itemid);
	
}
