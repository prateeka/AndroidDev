package com.androidcourse.grocery.factory;

import android.content.Context;

import com.androidcourse.grocery.dao.GroceryDAODBImpl;

public class GroceryFactoryDBImpl extends GroceryFactory {

	static public GroceryFactory getFactory(Context pContext) {
		if (factory == null) {
			factory = new GroceryFactoryDBImpl(pContext);
		}
		return factory;
	}

	private GroceryFactoryDBImpl(Context context) {
		super(context, new GroceryDAODBImpl(context));
	}

	/*-	@Override
	 public OnItemClickListener getItemViewListener() {
	 return null;
	 }
	 */
}
