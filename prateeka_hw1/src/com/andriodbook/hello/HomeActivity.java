package com.andriodbook.hello;


import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView tv = (TextView) findViewById(R.id.homeActivityLabel);

        Button closeButton = (Button) findViewById(R.id.homeActivityButton);
        closeButton.setOnClickListener(this);
    }

	public void onClick(View arg) {
		Intent intent = new Intent(this, TextActivity.class);
		startActivity(intent);
	}
}