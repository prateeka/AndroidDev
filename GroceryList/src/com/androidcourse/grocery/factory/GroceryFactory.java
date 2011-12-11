package com.androidcourse.grocery.factory;

import android.content.Intent;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.androidcourse.grocery.activity.item.display.DisplayTraderItemActivity;
import com.androidcourse.grocery.activity.item.display.DisplayTraderItemResultListener;
import com.androidcourse.grocery.activity.item.display.ItemViewListener;
import com.androidcourse.grocery.activity.item.display.TraderViewListener;
import com.androidcourse.grocery.activity.item.update.ItemAddUpdateActivity;
import com.androidcourse.grocery.dao.GroceryDAO;
import com.androidcourse.grocery.dao.GroceryDAODBImpl;
import com.androidcourse.grocery.listeners.result.ActivityResultListener;

public class GroceryFactory {

	protected static GroceryFactory factory;
	protected final DisplayTraderItemActivity activity;
	protected final GroceryDAO groceryDAO;

	static public GroceryFactory getFactory(DisplayTraderItemActivity context) {
		if (factory == null) {
			factory = new GroceryFactory(context);
		}
		return factory;
	}

	private GroceryFactory(DisplayTraderItemActivity context) {
		activity = context;
		groceryDAO = new GroceryDAODBImpl(context);
	}

	public GroceryDAO getGroceryDAO() {
		return groceryDAO;
	}

	public Intent getIntentToAddItem(Class<ItemAddUpdateActivity> toActivity) {
		return new Intent(activity, toActivity);
	}

	public ActivityResultListener getActivityResultListener() {
		return new DisplayTraderItemResultListener(
				activity,
				groceryDAO);
	}

	public OnItemClickListener getItemViewListener() {
		return new ItemViewListener(activity);
	}

	public OnItemSelectedListener getTraderViewListener() {
		return new TraderViewListener(activity);
	}

}
