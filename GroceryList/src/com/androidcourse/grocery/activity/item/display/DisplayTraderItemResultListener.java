package com.androidcourse.grocery.activity.item.display;

import android.app.Activity;
import android.content.Intent;

import com.androidcourse.grocery.dao.GroceryDAO;
import com.androidcourse.grocery.listeners.result.ActivityResultListener;
import com.androidcourse.grocery.util.GroceryConstants;

public class DisplayTraderItemResultListener implements ActivityResultListener {
	private final DisplayTraderItemActivity activity;
	private final GroceryDAO groceryDAO;

	public DisplayTraderItemResultListener(
			DisplayTraderItemActivity activity,
			GroceryDAO groceryDAO) {
		this.activity = activity;
		this.groceryDAO = groceryDAO;
	}

	@Override
	public void processResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			long selectedTraderID = data.getExtras().getLong(
					GroceryConstants.TRADER_ID);
			/*-		Toast.makeText(activity, "Trader selected id : "
							+ selectedTraderId, GroceryConstants.TOAST_DURATION).show();
			 */
			groceryDAO.addItem(data.getExtras());
			activity.refreshItem(selectedTraderID);
		}
	}
}
