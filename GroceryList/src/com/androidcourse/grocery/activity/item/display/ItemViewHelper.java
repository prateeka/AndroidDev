package com.androidcourse.grocery.activity.item.display;

import android.database.Cursor;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.androidcourse.grocery.R;
import com.androidcourse.grocery.dao.GroceryDAO;
import com.androidcourse.grocery.dao.GroceryDAODBImpl;
import com.androidcourse.grocery.factory.GroceryFactory;

public class ItemViewHelper {
	DisplayTraderItemActivity activity;
	GroceryDAO groceryDAO;
	GroceryFactory factory;

	ListView itemListView;

	public ItemViewHelper(DisplayTraderItemActivity activity,
			GroceryDAO groceryDAO, GroceryFactory factory) {
		super();
		this.activity = activity;
		this.groceryDAO = groceryDAO;
		this.factory = factory;
	}

	Cursor itemCursor;
	BaseAdapter itemAdapter;

	public void init() {
		itemListView = (ListView) activity.findViewById(R.id.listView1);
		refreshItemView(activity.getSelectedTraderId());
		/*-TranslationClickListener translationClickListener =(TranslationClickListener) factory
		 .getTranslationClickListener();
		 translationListView
		 .setOnItemClickListener(translationClickListener);
		 */
	}

	protected void refreshItemView(long traderId) {
		itemCursor = groceryDAO
				.getItemCursor(traderId);
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
