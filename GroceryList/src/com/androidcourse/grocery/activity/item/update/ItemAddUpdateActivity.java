package com.androidcourse.grocery.activity.item.update;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidcourse.grocery.R;
import com.androidcourse.grocery.dao.GroceryDAO;
import com.androidcourse.grocery.dao.GroceryDAODBImpl;
import com.androidcourse.grocery.factory.GroceryFactory;
import com.androidcourse.grocery.util.GroceryConstants;

public class ItemAddUpdateActivity extends Activity {

	private EditText itemName;
	private EditText itemQty;
	private EditText itemNote;
	private GroceryDAO groceryDAO;
	private GroceryFactory factory;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.additem);
		init();
	}

	private void init() {
		itemName = (EditText) findViewById(R.id.editText1);
		itemQty = (EditText) findViewById(R.id.editText2);
		itemNote = (EditText) findViewById(R.id.editText3);
		Button button = (Button) findViewById(R.id.operationButton);
		Toast.makeText(
				this,
				"operation selected is : "
						+ getIntentData(GroceryConstants.OPERATION),
				GroceryConstants.TOAST_DURATION).show();

		if (getIntentData(GroceryConstants.OPERATION) == GroceryConstants.UPDATE_ITEM_OPERATION) {
			button.setText(this.getResources().getString(
					R.string.UPDATE_BUTTON_LABEL));
			populateItemElements();
		} else if (getIntentData(GroceryConstants.OPERATION) == GroceryConstants.ADD_ITEM_OPERATION) {
			button.setText(this.getResources().getString(
					R.string.ADD_BUTTON_LABEL));
		}
	}

	private void populateItemElements() {
		Toast.makeText(
				this, "Trader and item selected id : "
						+ getIntentData(GroceryConstants.TRADER_ID) + ":"
						+ +getIntentData(GroceryConstants.ITEM_ID),
				GroceryConstants.TOAST_DURATION).show();
		factory = GroceryFactory.getFactory();
		groceryDAO = factory.getGroceryDAO();
		Cursor itemCursor = getItemCursor();
		setText(itemName, itemCursor,
				GroceryDAODBImpl.TABLE_ITEM_COLUMN_ITEM_NAME);
		setText(itemQty, itemCursor,
				GroceryDAODBImpl.TABLE_ITEM_COLUMN_ITEM_QTY);
		/*-		setText(itemNote, itemCursor,
		 GroceryDAODBImpl.TABLE_ITEM_COLUMN_ITEM_NOTE);
		 */
		itemCursor.close();
	}

	private void setText(EditText editText, Cursor itemCursor,
			String columnName) {
		if (columnName.equals(GroceryDAODBImpl.TABLE_ITEM_COLUMN_ITEM_NAME)) {
			int idx = itemCursor
					.getColumnIndexOrThrow(columnName);
			Log.d(this.getClass().getName(),
					"setText- idx:value for columnName is : " + idx + ":"
							+ itemCursor.getString(idx));
			setText(editText, itemCursor.getString(idx));
		} else if (columnName
				.equals(GroceryDAODBImpl.TABLE_ITEM_COLUMN_ITEM_QTY)) {
			setText(editText, Float.toString(itemCursor.getFloat(itemCursor
					.getColumnIndexOrThrow(columnName))));
		} else if (columnName
				.equals(GroceryDAODBImpl.TABLE_ITEM_COLUMN_ITEM_NOTE)) {
			setText(editText, itemCursor.getString(itemCursor
					.getColumnIndexOrThrow(columnName)));
		}
	}

	private void setText(EditText editText, String string) {
		editText.setText(string);
	}

	protected Cursor getItemCursor() {
		long itemId = getIntent().getExtras().getLong(GroceryConstants.ITEM_ID);
		Cursor itemCursor = groceryDAO.getItemCursorForItemId(itemId);
		return itemCursor;
	}

	private long getIntentData(String operation) {
		return this.getIntent().getExtras().getLong(operation);
	}

	public void onAddButtonClick(View view) {
		Intent intent = prepareIntentWithItemData();
		returnToParentActivity(intent);
	}

	protected Intent prepareIntentWithItemData() {
		Intent intent = this.getIntent();
		intent.putExtra(GroceryConstants.ITEM_NAME,
				itemName.getText().toString());
		intent.putExtra(GroceryConstants.ITEM_QTY,
				Float.parseFloat(itemQty.getText().toString()));
		return intent;
	}

	protected void returnToParentActivity(Intent intent) {
		this.setResult(Activity.RESULT_OK, intent);
		this.finish();
	}

	public void onClearButtonClick(View view) {
		setText(itemName, "");
		setText(itemQty, "");
		setText(itemNote, "");
	}
}