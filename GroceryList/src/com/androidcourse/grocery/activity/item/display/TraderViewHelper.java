package com.androidcourse.grocery.activity.item.display;

import android.database.Cursor;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.androidcourse.grocery.R;
import com.androidcourse.grocery.dao.GroceryDAODBImpl;
import com.androidcourse.grocery.dao.TraderContentProvider;
import com.androidcourse.grocery.util.GroceryConstants;

public class TraderViewHelper {
	private Spinner traderView;
	private final DisplayTraderItemActivity activity;
	private final OnItemSelectedListener onItemSelectedListener;
	
	public TraderViewHelper(DisplayTraderItemActivity activity,
			OnItemSelectedListener onItemSelectedListener) {
		this.activity = activity;
		this.onItemSelectedListener = onItemSelectedListener;
	}
	
	public void init() {
		traderView = (Spinner) activity.findViewById(R.id.spinner1);
		Cursor traderCursor = activity.getContentResolver().query(
				TraderContentProvider.CONTENT_URI,
				null,
				null,
				null,
				null);
		
		// Cursor traderCursor = groceryDAO.getTraderCursor();
		activity.startManagingCursor(traderCursor);
		traderView
				.setAdapter(getTraderAdapter(traderCursor));
		traderView.setSelection(GroceryConstants.DEFAULT_TRADER_INDEX);
		traderView.setOnItemSelectedListener(onItemSelectedListener);
	}
	
	public SpinnerAdapter getTraderAdapter(Cursor traderCursor) {
		String[] from = new String[] { GroceryDAODBImpl.TABLE_TRADER_COLUMN_TRADER_NAME };
		int[] to = new int[] { android.R.id.text1 };
		
		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(activity,
				android.R.layout.simple_spinner_item, traderCursor, from, to);
		
		cursorAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return cursorAdapter;
	}
	
	public long getSelectedItemId() {
		return traderView.getSelectedItemId();
	}
	
}
