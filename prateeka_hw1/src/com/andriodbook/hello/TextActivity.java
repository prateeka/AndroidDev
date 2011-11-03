package com.andriodbook.hello;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TextActivity extends Activity implements OnClickListener{
	
	static Random random = new Random();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text);
		addRandomTextForTextView();
		addHomeButton();
	}

	void addHomeButton() {
		Button homeButton = (Button) findViewById(R.id.textActivityHomeButton);
		homeButton.setOnClickListener(this);
	}

	void addRandomTextForTextView() {
		TextView tv = (TextView)getViewId(R.id.textActivityRandomLabel); 	
		String randomStringArray[] = getRandomStringArray();
		setTextForTextView(tv, randomStringArray);
	}

	View getViewId(int viewId) {
		return findViewById(viewId);
	}

	void setTextForTextView(TextView tv, String[] randomStringArray) {
		int randomIndex = getRandomInt(randomStringArray.length);
		tv.setText(randomStringArray[randomIndex]);
	}

	int getRandomInt(int higherExclusiveInt) {
		return random.nextInt(higherExclusiveInt);
	}

	String[] getRandomStringArray() {
		Resources res = this.getResources();
		return res.getStringArray(R.array.textActivityRandomLabelArray);
	}
	
	public void onClick(View arg) {
		 Intent intent = new Intent(Intent.ACTION_MAIN);
         intent.addCategory(Intent.CATEGORY_HOME);
         startActivity(intent);
	}
}
