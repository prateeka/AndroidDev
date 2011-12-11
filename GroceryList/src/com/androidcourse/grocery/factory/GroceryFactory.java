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

	static public GroceryFactory getFactory(Context context) {
		if (factory == null) {
			factory = new GroceryFactory(context);
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

	protected GroceryFactory(Context context,
			GroceryDAO groceryDAO) {
		this.context = context;
		this.groceryDAO = groceryDAO;
	}

	public Intent getIntentToAddNewItem(Class<ItemAddUpdateActivity> toActivity) {
		return new Intent(context, toActivity);
	}

	public OnItemClickListener getItemViewListener() {
		return new ItemViewListener(context);
	}

	public ActivityResultListener getActivityResultListener() {
		return new DisplayTraderItemResultListener(
				(DisplayTraderItemActivity) context,
				groceryDAO);
	}

	public OnItemSelectedListener getTraderViewListener() {
		return new TraderViewListener((DisplayTraderItemActivity) context);
	}

}
