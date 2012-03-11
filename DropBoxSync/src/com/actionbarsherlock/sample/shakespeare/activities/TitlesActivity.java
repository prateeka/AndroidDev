package com.actionbarsherlock.sample.shakespeare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.util.Log;
import android.view.MenuInflater;
import android.widget.Toast;

import com.actionbarsherlock.sample.shakespeare.R;
import com.actionbarsherlock.sample.shakespeare.email.Mail;
import com.actionbarsherlock.sample.shakespeare.fragments.DetailsFragment;
import com.actionbarsherlock.sample.shakespeare.fragments.OnArticleSelectedListener;

public class TitlesActivity extends FragmentActivity implements
		OnArticleSelectedListener {
	
	private TitleHandler titleHandler;
	private DetailHandler detailHandler;
	private Menu menu;
	
	private static final String TAG = "TitlesActivity";
	
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
		sendEmail();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar, menu);
		this.menu = menu;
		return true;
	}
	
	@Override
	public void onArticleSelected(String titleSelected) {
		enableAllMenuItems();
		detailHandler.showDetails(titleSelected);
	}
	
	public void detailWorkedOn(String titleWorkedOn, String operation) {
		if (!operation.equals(DetailsFragment.CANCELLED)) {
			Toast toast = Toast.makeText(
					getApplicationContext(),
					titleWorkedOn + " " + operation,
					1000);
			toast.show();
		}
		enableAllMenuItems();
		titleHandler.refreshTitles();
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
				resetTitlePositionSelected();
				detailHandler.addNote();
				disableMenuItem(R.id.menuUpdate);
				disableMenuItem(R.id.menuDelete);
				break;
			case R.id.menuUpdate:
				handled = true;
				detailHandler.updateNote();
				disableMenuItem(R.id.menuAdd);
				disableMenuItem(R.id.menuDelete);
				break;
			case R.id.menuDelete:
				handled = true;
				detailHandler.deleteNote();
				break;
		}
		
		if (handled) {
			return handled;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void resetTitlePositionSelected() {
		titleHandler.resetTitlePositionSelected();
	}
	
	private void disableMenuItem(int item) {
		menu.findItem(item).setVisible(false);
	}
	
	private void enableAllMenuItems() {
		enableMenuItem(R.id.menuAdd);
		enableMenuItem(R.id.menuUpdate);
		enableMenuItem(R.id.menuDelete);
	}
	
	private void enableMenuItem(int item) {
		menu.findItem(item).setVisible(true);
	}
	
	private void sendEmail() {
		Mail mail = new Mail();
		try {
			mail.send();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			Log.e(TAG, ex.toString());
		}
	}
}
