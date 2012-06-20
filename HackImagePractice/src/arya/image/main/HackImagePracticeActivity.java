package arya.image.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class HackImagePracticeActivity extends Activity {
	private static final int SELECT_IMAGE = 1;
	private ImageClickListener imageClickListener;
	private ImageView imageView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		imageClickListener = new ImageClickListener();
		imageView = (ImageView) findViewById(R.id.imageView);
		imageView.setOnClickListener(imageClickListener);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if ((requestCode == SELECT_IMAGE) && (resultCode == Activity.RESULT_OK)) {
			imageClickListener.onActivityResult(data);
		}
	}
	
	private class ImageClickListener implements OnClickListener {
		private static final String TAG = "ImageClickListener";
		
		@Override
		public void onClick(View v) {
			Log.d(TAG, "imageView clicked");
			Intent imageChooser = new Intent(Intent.ACTION_GET_CONTENT);
			imageChooser.setType("image/*");
			startActivityForResult(imageChooser, SELECT_IMAGE);
		}
		
		public void onActivityResult(Intent data) {
			if (data.getData() == null) {
				Log.d(TAG, "data.getData() is null");
			} else {
				Uri uri = data.getData();
				Log.d(TAG, uri.toString());
				imageView.setImageURI(uri);
			}
		}
	}
	
}