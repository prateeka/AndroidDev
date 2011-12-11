package com.androidcourse.grocery.activity.item.update;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.androidcourse.grocery.R;

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
	}

	public void onClearButtonClick(View view) {
		itemName.setText("");
		itemQty.setText("");
		itemNotes.setText("");
	}

	public void onAddButtonClick(View view) {
		Intent intent = getIntentWithItemData();
		returnToParentActivity(intent);
	}

	protected Intent getIntentWithItemData() {
		Intent intent = this.getIntent();
		intent.putExtra(getResources().getString(R.string.itemName),
				itemName.getText().toString());
		intent.putExtra(getResources().getString(R.string.itemQty),
				Float.parseFloat(itemQty.getText().toString()));
		return intent;
	}

	protected void returnToParentActivity(Intent intent) {
		this.setResult(Activity.RESULT_OK, intent);
		this.finish();
	}
}