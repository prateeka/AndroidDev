package com.androidcourse.grocery.dao;

import android.database.Cursor;
import android.os.Bundle;

public interface GroceryDAO {

	long addItem(Bundle bundle);

	Cursor getTraderCursor();

	Cursor getItemCursorForTraderId(long traderId);

	Cursor getItemCursorForItemId(long itemId);

	void updateItem(long itemId, String text, float parseFloat);
}
