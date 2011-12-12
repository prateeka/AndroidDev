package com.androidcourse.grocery.factory;

import android.content.Context;
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
	protected final Context context;
	protected final GroceryDAO groceryDAO;

	public static GroceryFactory getFactory(Context context) {
		if (factory == null) {
			factory = new GroceryFactory(context);
		}
		return factory;
	}

	public static GroceryFactory getFactory() {
		if (factory == null) {
			throw new RuntimeException(
					"Factory instance needs a context");
		}
		return factory;
	}

	private GroceryFactory(Context context) {
		this.context = context;
		groceryDAO = new GroceryDAODBImpl(context);
	}

	public GroceryDAO getGroceryDAO() {
		return groceryDAO;
	}

	public Intent getIntentToAddItem(Context context,
			Class<ItemAddUpdateActivity> toActivity) {
		return new Intent(context, toActivity);
	}

	public ActivityResultListener getActivityResultListener(
			DisplayTraderItemActivity activity) {
		return new DisplayTraderItemResultListener(activity);
	}

	public OnItemClickListener getItemViewListener(
			DisplayTraderItemActivity activity) {
		return new ItemViewListener(activity);
	}

	public OnItemSelectedListener getTraderViewListener(
			DisplayTraderItemActivity activity) {
		return new TraderViewListener(activity);
	}

}
