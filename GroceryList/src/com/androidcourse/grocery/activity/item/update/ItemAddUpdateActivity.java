package com.androidcourse.grocery.activity.item.update;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidcourse.grocery.R;
import com.androidcourse.grocery.dao.GroceryDAO;
import com.androidcourse.grocery.dao.GroceryDAODBImpl;
import com.androidcourse.grocery.dao.ItemContentProvider;
import com.androidcourse.grocery.factory.GroceryFactory;
import com.androidcourse.grocery.util.GroceryConstants;
import com.androidcourse.grocery.util.GroceryUtilFunctions;

public class ItemAddUpdateActivity extends Activity {
	
	private static final String TAG = "ItemAddUpdateActivity";
	
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
		
		if (GroceryUtilFunctions.getIntentData(
				getIntent(),
				GroceryConstants.OPERATION)
				== GroceryConstants.UPDATE_ITEM_OPERATION) {
			button.setText(this.getResources().getString(
					R.string.UPDATE_BUTTON_LABEL));
			populateItemElements();
		} else if (GroceryUtilFunctions.getIntentData(
				getIntent(),
				GroceryConstants.OPERATION)
				== GroceryConstants.ADD_ITEM_OPERATION) {
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
		setText(itemName,
				itemCursor,
				GroceryDAODBImpl.TABLE_ITEM_COLUMN_ITEM_NAME);
		setText(itemQty,
				itemCursor,
				GroceryDAODBImpl.TABLE_ITEM_COLUMN_ITEM_QTY);
		setText(itemNote,
				itemCursor,
				GroceryDAODBImpl.TABLE_ITEM_COLUMN_ITEM_NOTE);
		
		itemCursor.close();
	}
	
	private void setText(EditText editText,
			Cursor itemCursor,
			String columnName) {
		if (columnName.equals(GroceryDAODBImpl.TABLE_ITEM_COLUMN_ITEM_NAME)) {
			setText(editText, itemCursor.getString(itemCursor
					.getColumnIndexOrThrow(columnName)));
		} else if (columnName
				.equals(GroceryDAODBImpl.TABLE_ITEM_COLUMN_ITEM_QTY)) {
			setText(editText,
					Float.toString(itemCursor.getFloat(itemCursor
							.getColumnIndexOrThrow(columnName))));
		} else if (columnName
				.equals(GroceryDAODBImpl.TABLE_ITEM_COLUMN_ITEM_NOTE)) {
			setText(editText,
					itemCursor.getString(itemCursor
							.getColumnIndexOrThrow(columnName)));
		}
	}
	
	private void setText(EditText editText, String string) {
		editText.setText(string);
	}
	
	protected Cursor getItemCursor() {
		long itemId = GroceryUtilFunctions.getIntentData(getIntent(),
				GroceryConstants.ITEM_ID);
		Uri uri = Uri.withAppendedPath(
				ItemContentProvider.CONTENT_URI,
				Long.toString(itemId));
		
		Cursor itemCursor = getContentResolver().query(
				uri, null, null, null, null);
		// Cursor itemCursor = groceryDAO.getItemCursor(itemId);
		if (itemCursor != null) {
			itemCursor.moveToFirst();
		}
		return itemCursor;
	}
	
	public void onOperationButtonClick(View view) {
		if (GroceryUtilFunctions.getIntentData(
				getIntent(),
				GroceryConstants.OPERATION
				) == GroceryConstants.ADD_ITEM_OPERATION) {
			Log.d(
					this.getClass().getName(),
					"onOperationButtonClick() - name, qty, note, traderId are : "
							+
							getText(itemName)
							+ ":" +
							Float.parseFloat(getText(itemQty))
							+ ":" +
							getText(itemNote)
							+ ":" +
							GroceryUtilFunctions.getIntentData(getIntent(),
									GroceryConstants.TRADER_ID));
			addItem(getText(itemName),
					getText(itemQty),
					getText(itemNote),
					GroceryUtilFunctions.getIntentData(getIntent(),
							GroceryConstants.TRADER_ID));
			returnToParentActivity(this.getIntent());
		} else if (GroceryUtilFunctions.getIntentData(
				getIntent(),
				GroceryConstants.OPERATION)
				== GroceryConstants.UPDATE_ITEM_OPERATION) {
			groceryDAO.updateItem(
					GroceryUtilFunctions.getIntentData(
							getIntent(),
							GroceryConstants.ITEM_ID),
					getText(itemName),
					Float.parseFloat(getText(itemQty)),
					getText(itemNote),
					GroceryUtilFunctions.getIntentData(getIntent(),
							GroceryConstants.TRADER_ID)
					);
			returnToParentActivity(this.getIntent());
		}
	}
	
	protected void addItem(String itemName, String itemQty, String itemNote,
			long traderID) {
		ContentValues itemToAdd = createItemContentValues(
				itemName, itemQty, itemNote, traderID);
		ContentResolver cr = getContentResolver();
		Uri uri = ItemContentProvider.CONTENT_URI;
		Log.d(TAG, "item insert uri:" + uri);
		Uri insertedUri = cr.insert(uri, itemToAdd);
		Log.d(TAG, "inserted uri:" + insertedUri);
		
		/*-		groceryDAO.addItem(
		 getText(itemName),
		 Float.parseFloat(getText(itemQty)),
		 getText(itemNote),
		 GroceryUtilFunctions.getIntentData(getIntent(),
		 GroceryConstants.TRADER_ID)
		 );
		 */
	}
	
	private ContentValues createItemContentValues(
			String itemName, String itemQty, String itemNote, long traderId) {
		ContentValues itemContentValues = new ContentValues();
		itemContentValues.put(
				GroceryDAODBImpl.TABLE_ITEM_COLUMN_ITEM_NAME,
				itemName);
		itemContentValues.put(
				GroceryDAODBImpl.TABLE_ITEM_COLUMN_ITEM_QTY,
				itemQty);
		itemContentValues.put(
				GroceryDAODBImpl.TABLE_ITEM_COLUMN_ITEM_NOTE,
				itemNote);
		itemContentValues.put(
				GroceryDAODBImpl.TABLE_ITEM_COLUMN_TRADER_REF,
				traderId);
		return itemContentValues;
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