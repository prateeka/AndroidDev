package com.androidcourse.grocery.factory;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView.OnItemSelectedListener;

import com.androidcourse.grocery.activity.item.display.DisplayTraderItemActivity;
import com.androidcourse.grocery.activity.item.display.DisplayTraderItemResultListener;
import com.androidcourse.grocery.activity.item.display.TraderViewListener;
import com.androidcourse.grocery.activity.item.update.ItemAddUpdateActivity;
import com.androidcourse.grocery.dao.GroceryDAO;
import com.androidcourse.grocery.listeners.result.ActivityResultListener;

public abstract class GroceryFactory {

	protected static GroceryFactory factory;
	protected final Context context;
	protected final GroceryDAO groceryDAO;

	public GroceryDAO getGroceryDAO() {
		return groceryDAO;
	}

	protected GroceryFactory(Context context,
			GroceryDAO groceryDAO) {
		this.context = context;
		this.groceryDAO = groceryDAO;
	}

	public Intent getIntentToAddNewItem(Class<ItemAddUpdateActivity> toActivity) {
		return new Intent(context, toActivity);
	}

	// abstract public OnItemClickListener getItemListener();

	public ActivityResultListener getActivityResultListener() {
		return new DisplayTraderItemResultListener(
				(DisplayTraderItemActivity) context,
				groceryDAO);
	}

	public OnItemSelectedListener getItemViewListener() {
		return new TraderViewListener((DisplayTraderItemActivity) context);
	}

}
