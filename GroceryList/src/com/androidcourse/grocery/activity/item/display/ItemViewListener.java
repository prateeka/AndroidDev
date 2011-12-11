package com.androidcourse.grocery.activity.item.display;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ItemViewListener implements OnItemClickListener {

	Context context;

	public ItemViewListener(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long rowID) {
		/*-		Toast.makeText(context, position + " No action defined",
		 GroceryConstants.TOAST_DURATION).show();
		 */}

}