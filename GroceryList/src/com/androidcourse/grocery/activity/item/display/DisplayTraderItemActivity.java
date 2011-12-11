package com.androidcourse.grocery.activity.item.display;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.androidcourse.grocery.R;
import com.androidcourse.grocery.activity.item.update.ItemAddUpdateActivity;
import com.androidcourse.grocery.dao.GroceryDAO;
import com.androidcourse.grocery.factory.GroceryFactory;
import com.androidcourse.grocery.listeners.result.ActivityResultListener;

public class DisplayTraderItemActivity extends Activity {

	private static final int ID_ADD_ITEM = 1;
	GroceryFactory factory;
	ActivityResultListener activityResultListener;

	GroceryDAO groceryDAO;
	TraderViewHelper traderViewHelper;
	ItemViewHelper itemViewHelper;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaytraderitem);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.displaytraderitemmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		boolean handled = false;
		/*-Toast.makeText(this, "onOptionsItemSelected Trader selected id: "
				+ getSelectedTraderId(), GroceryConstants.TOAST_DURATION)
				.show();*/
		if (item.getItemId() == R.id.menu_AddItem) {
			Intent intent = factory
					.getIntentToAddNewItem(ItemAddUpdateActivity.class);
			addDataToIntent(intent);
			this.startActivityForResult(intent, ID_ADD_ITEM);
			handled = true;
		}
		return handled;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		activityResultListener.processResult(requestCode, resultCode, data);
	}

	protected void init() {
		factory = GroceryFactory.getFactory(this);
		groceryDAO = factory.getGroceryDAO();
		activityResultListener = factory.getActivityResultListener();
		initTrader();
		initItem();
	}

	protected void initTrader() {
		traderViewHelper = new TraderViewHelper(this, groceryDAO, factory);
		traderViewHelper.init();
	}

	protected void initItem() {
		itemViewHelper = new ItemViewHelper(this, groceryDAO, factory);
		itemViewHelper.init();
	}

	private void addDataToIntent(Intent intent) {
		intent.putExtra(getResources().getString(R.string.traderId),
				getSelectedTraderId());
	}

	long getSelectedTraderId() {
		return traderViewHelper.getSelectedItemId();
	}

	void refreshItem(long traderId) {
		itemViewHelper.refreshItemView(traderId);
	}

}
