package com.actionbarsherlock.sample.shakespeare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.MenuInflater;
import android.widget.Toast;

import com.actionbarsherlock.sample.shakespeare.R;
import com.actionbarsherlock.sample.shakespeare.fragments.OnArticleSelectedListener;

public class TitlesActivity extends FragmentActivity implements
		OnArticleSelectedListener {
	
	private TitleHandler titleHandler;
	private DetailHandler detailHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.activity_titles);
		setContentView(R.layout.main);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		detailHandler = DetailHandler.getInstance();
		titleHandler = TitleHandler.getInstance();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.title, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean handled = false;
		switch (item.getItemId()) {
			case android.R.id.home:
				handled = true;
				Intent intent = new Intent(this, TitlesActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;
			case R.id.menuAdd:
				handled = true;
				detailHandler.addNote();
				break;
			case R.id.menuUpdate:
				handled = true;
				Toast toast = Toast.makeText(
						getApplicationContext(),
						"Update Menu pressed",
						1000);
				toast.show();
				detailHandler.updateNote();
				break;
		}
		
		if (handled) {
			return handled;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onArticleSelected(String titleSelected) {
		detailHandler.showDetails(titleSelected);
	}
	
	public void refreshTitles() {
		titleHandler.refreshTitles();
	}
}
