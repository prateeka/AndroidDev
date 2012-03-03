package com.androidcourse.grocery.activity.item.display;

/*
 * This acts as a view listener when user clicks on shopping items.
 */
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.androidcourse.grocery.util.GroceryConstants;

public class ItemViewListener implements OnItemClickListener {
	
	DisplayTraderItemActivity activity;
	
	public ItemViewListener(DisplayTraderItemActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long rowId) {
		/*-		Toast.makeText(activity,
		 " No action defined: position:rowID" + position + ":" + rowId,
		 GroceryConstants.TOAST_DURATION).show();
		 */
		activity.startItemAddUpdateActivity(
				GroceryConstants.UPDATE_ITEM_OPERATION,
				rowId);
	}
}
