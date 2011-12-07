package com.androidcourse.hw4.activity.addTranslations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.androidcourse.hw4.R;

public class AddTranslation extends Activity {

	private EditText translationText1;
	private EditText translationText2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addtranslation);
		init();
	}

	private void init() {
		translationText1 = (EditText) findViewById(R.id.translationText1);
		translationText2 = (EditText) findViewById(R.id.translationText2);
	}

	public void onClearButtonClick(View view) {
		translationText1.setText("");
		translationText2.setText("");
	}

	public void onAddButtonClick(View view) {
		Intent intent = getIntentWithTranslations();
		returnToParentActivity(intent);
	}

	protected Intent getIntentWithTranslations() {
		Intent intent = this.getIntent();
		intent.putExtra(getResources().getString(R.string.translationLang1),
				translationText1.getText().toString());
		intent.putExtra(getResources().getString(R.string.translationLang2),
				translationText2.getText().toString());
		return intent;
	}

	protected void returnToParentActivity(Intent intent) {
		this.setResult(Activity.RESULT_OK, intent);
		this.finish();
	}
}