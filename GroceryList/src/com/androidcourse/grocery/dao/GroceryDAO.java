package com.androidcourse.grocery.dao;

import android.database.Cursor;

public interface GroceryDAO {

	Cursor getTraderCursor();

	Cursor getItemCursorForTraderId(long traderId);

	Cursor getItemCursorForItemId(long itemId);

	int updateItem(long itemId, String text, float parseFloat, long traderId);

	long addItem(String text, float parseFloat, long traderId);
}
