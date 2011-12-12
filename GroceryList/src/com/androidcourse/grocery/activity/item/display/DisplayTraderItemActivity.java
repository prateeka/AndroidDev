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
import com.androidcourse.grocery.util.GroceryConstants;
import com.androidcourse.grocery.util.GroceryUtilFunctions;

public class DisplayTraderItemActivity extends Activity {

	private GroceryFactory factory;
	private ActivityResultListener activityResultListener;

	private GroceryDAO groceryDAO;
	private TraderViewHelper traderViewHelper;
	private ItemViewHelper itemViewHelper;

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
			handled = startItemAddUpdateActivity(
					GroceryConstants.ADD_ITEM_OPERATION,
					GroceryConstants.ID_UNDEFINED);
		}
		return handled;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			long selectedTraderID = GroceryUtilFunctions.getIntentData(data,
					GroceryConstants.TRADER_ID);
			refreshItemList(selectedTraderID);
		}
	}

	protected boolean startItemAddUpdateActivity(int operation, long itemId) {
		/*-		Toast.makeText(this,
		 "DisplayTraderItemActivity.startItemAddUpdateActivity - operation:itemId is  "
		 + operation + ":" + itemId,
		 GroceryConstants.TOAST_DURATION)
		 .show();
		 */
		Intent intent = factory
				.getIntentToAddItem(this, ItemAddUpdateActivity.class);
		addDataToIntent(intent, GroceryConstants.OPERATION, operation);

		long traderId = getSelectedTraderId();
		addDataToIntent(intent, GroceryConstants.TRADER_ID, traderId);

		if (operation == GroceryConstants.UPDATE_ITEM_OPERATION) {
			addDataToIntent(intent, GroceryConstants.ITEM_ID, itemId);
		}
		this.startActivityForResult(intent, operation);
		return true;
	}

	protected void init() {
		factory = GroceryFactory.getFactory(this);
		groceryDAO = factory.getGroceryDAO();
		activityResultListener = factory.getActivityResultListener(this);
		initTrader();
		initItem();
	}

	protected void initTrader() {
		traderViewHelper = new TraderViewHelper(this, groceryDAO,
				factory.getTraderViewListener(this));
		traderViewHelper.init();
	}

	protected void initItem() {
		itemViewHelper = new ItemViewHelper(this, groceryDAO,
				factory.getItemViewListener(this));
		itemViewHelper.init();
	}

	private void addDataToIntent(Intent intent, String key, long id) {
		intent.putExtra(key, id);
	}

	long getSelectedTraderId() {
		return traderViewHelper.getSelectedItemId();
	}

	void refreshItemList(long traderId) {
		itemViewHelper.refreshItemView(traderId);
	}

}
