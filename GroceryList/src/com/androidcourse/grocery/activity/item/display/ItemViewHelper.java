package com.androidcourse.grocery.activity.item.display;

import android.database.Cursor;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.androidcourse.grocery.R;
import com.androidcourse.grocery.dao.GroceryDAODBImpl;
import com.androidcourse.grocery.dao.ItemContentProvider;

public class ItemViewHelper {
	private final DisplayTraderItemActivity activity;
	private Cursor itemCursor;
	private BaseAdapter itemAdapter;
	
	private ListView itemListView;
	private final OnItemClickListener itemViewListener;
	
	public ItemViewHelper(DisplayTraderItemActivity activity,
			OnItemClickListener itemViewListener) {
		super();
		this.activity = activity;
		this.itemViewListener = itemViewListener;
	}
	
	public void init() {
		itemListView = (ListView) activity.findViewById(R.id.listView1);
		refreshItemView(activity.getSelectedTraderId());
		itemListView.setOnItemClickListener(itemViewListener);
		activity.registerForContextMenu(itemListView);
	}
	
	protected void refreshItemView(long traderId) {
		itemCursor = activity.getContentResolver().query(
				ItemContentProvider.CONTENT_URI,
				null,
				GroceryDAODBImpl.TABLE_ITEM_COLUMN_TRADER_REF + "=" + traderId,
				null,
				null);
		
		activity.startManagingCursor(itemCursor);
		itemAdapter = getItemAdapter(itemCursor);
		itemListView.setAdapter(itemAdapter);
		// translationAdapter.notifyDataSetChanged();
	}
	
	public BaseAdapter getItemAdapter(Cursor itemCursor) {
		String[] from = new String[] {
				GroceryDAODBImpl.TABLE_ITEM_COLUMN_ITEM_NAME,
				GroceryDAODBImpl.TABLE_ITEM_COLUMN_ITEM_QTY };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };
		
		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(activity,
				android.R.layout.two_line_list_item, itemCursor, from,
				to);
		return cursorAdapter;
	}
}
