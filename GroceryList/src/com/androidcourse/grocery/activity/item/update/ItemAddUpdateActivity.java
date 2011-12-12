package com.androidcourse.grocery.activity.item.update;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidcourse.grocery.R;
import com.androidcourse.grocery.dao.GroceryDAO;
import com.androidcourse.grocery.dao.GroceryDAODBImpl;
import com.androidcourse.grocery.factory.GroceryFactory;
import com.androidcourse.grocery.util.GroceryConstants;
import com.androidcourse.grocery.util.GroceryUtilFunctions;

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
		factory = GroceryFactory.getFactory();
		groceryDAO = factory.getGroceryDAO();
		itemName = (EditText) findViewById(R.id.editText1);
		itemQty = (EditText) findViewById(R.id.editText2);
		itemNote = (EditText) findViewById(R.id.editText3);
		Button button = (Button) findViewById(R.id.operationButton);
		
		Log.d(this.getClass().getName(),
				"init() - operation selected is : "
						+ GroceryUtilFunctions.getIntentData(getIntent(),
								GroceryConstants.OPERATION));
		
		if (GroceryUtilFunctions.getIntentData(getIntent(),
				GroceryConstants.OPERATION) == GroceryConstants.UPDATE_ITEM_OPERATION) {
			button.setText(this.getResources().getString(
					R.string.UPDATE_BUTTON_LABEL));
			populateItemElements();
		} else if (GroceryUtilFunctions.getIntentData(getIntent(),
				GroceryConstants.OPERATION) == GroceryConstants.ADD_ITEM_OPERATION) {
			button.setText(this.getResources().getString(
					R.string.ADD_BUTTON_LABEL));
		}
	}
	
	private void populateItemElements() {
		Log.d(this.getClass().getName(),
				"populateItemElements() - Trader and item selected id : "
						+ GroceryUtilFunctions.getIntentData(getIntent(),
								GroceryConstants.TRADER_ID)
						+ ":"
						+ GroceryUtilFunctions.getIntentData(getIntent(),
								GroceryConstants.ITEM_ID));
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
		long itemId = GroceryUtilFunctions.getIntentData(getIntent(),
				GroceryConstants.ITEM_ID);
		Cursor itemCursor = groceryDAO.getItemCursorForItemId(itemId);
		return itemCursor;
	}
	
	public void onOperationButtonClick(View view) {
		if (GroceryUtilFunctions.getIntentData(getIntent(),
				GroceryConstants.OPERATION) == GroceryConstants.ADD_ITEM_OPERATION) {
			Log.d(this.getClass().getName(),
					"onOperationButtonClick() - name, qty, traderId are : "
							+ getText(itemName)
							+ ":" +
							Float.parseFloat(getText(itemQty))
							+ ":" +
							GroceryUtilFunctions.getIntentData(getIntent(),
									GroceryConstants.TRADER_ID));
			groceryDAO.addItem(
					getText(itemName),
					Float.parseFloat(getText(itemQty)),
					GroceryUtilFunctions.getIntentData(getIntent(),
							GroceryConstants.TRADER_ID)
					);
			returnToParentActivity(this.getIntent());
		} else if (GroceryUtilFunctions.getIntentData(
				getIntent(), GroceryConstants.OPERATION)
				== GroceryConstants.UPDATE_ITEM_OPERATION) {
			groceryDAO.updateItem(
					GroceryUtilFunctions.getIntentData(
							getIntent(), GroceryConstants.ITEM_ID),
					getText(itemName),
					Float.parseFloat(getText(itemQty)),
					GroceryUtilFunctions.getIntentData(getIntent(),
							GroceryConstants.TRADER_ID)
					);
			returnToParentActivity(this.getIntent());
		}
	}
	
	private String getText(EditText editText) {
		return editText.getText().toString();
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