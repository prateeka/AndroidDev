package arya.image.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class HackImagePracticeActivity extends Activity implements
		OnClickListener {
	private ImageProcessor imageProcessor;
	private ImageView imageView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		imageProcessor = new ImageProcessor(this);
		imageView = (ImageView) findViewById(R.id.imageView);
		imageView.setOnClickListener(this);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			imageProcessor.loadPicture(requestCode, data);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.appmenu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		boolean handled = false;
		
		if (item.getItemId() == R.id.menu_Flip) {
			handled = imageProcessor.flipImage();
		}
		return handled;
	}
	
	@Override
	public void onClick(View v) {
		imageProcessor.initiatePictureLoad(ImageProcessor.LOAD_FROM_GALLERY);
	}
	
	ImageView getImageView() {
		return imageView;
	}
	
	void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	
}