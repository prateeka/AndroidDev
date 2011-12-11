package com.androidcourse.grocery.activity.item.display;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class TraderViewListener implements OnItemSelectedListener {

	private final DisplayTraderItemActivity activity;

	public TraderViewListener(DisplayTraderItemActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		/*-		Toast.makeText(activity,
		 "Trader clicked position:id- " + pos + ":" + id,
		 GroceryConstants.TOAST_DURATION).show();
		 */activity.refreshItem(id);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

}
