package com.androidcourse.grocery.activity.item.update;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidcourse.grocery.R;
import com.androidcourse.grocery.util.GroceryConstants;

public class ItemAddUpdateActivity extends Activity {

	private EditText itemName;
	private EditText itemQty;
	private EditText itemNotes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.additem);
		init();
	}

	private void init() {
		itemName = (EditText) findViewById(R.id.editText1);
		itemQty = (EditText) findViewById(R.id.editText2);
		itemNotes = (EditText) findViewById(R.id.editText3);

		Toast.makeText(
				this,
				"operation selected is : "
						+ getIntentData(GroceryConstants.OPERATION),
				GroceryConstants.TOAST_DURATION).show();

		if (getIntentData(GroceryConstants.OPERATION) == GroceryConstants.UPDATE_ITEM_OPERATION) {
			populateItemElements();
		}
	}

	private void populateItemElements() {
		Toast.makeText(
				this,
				"Trader and item selected id : "
						+ getIntentData(GroceryConstants.TRADER_ID) + ":"
						+ +getIntentData(GroceryConstants.ITEM_ID),
				GroceryConstants.TOAST_DURATION).show();
	}

	private long getIntentData(String operation) {
		return this.getIntent().getExtras().getLong(operation);
	}

	public void onClearButtonClick(View view) {
		itemName.setText("");
		itemQty.setText("");
		itemNotes.setText("");
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
}