package arya.image.main;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
			
			Uri uri = data.getData();
			if (uri == null) {
				Log.d(TAG, "data.getData() is null");
			} else {
				setPicFromUri(uri);
			}
		}
		
		private void setPicFromUri(Uri uri) {
			try {
				// Get the dimensions of the View
				int targetW = imageView.getWidth();
				int targetH = imageView.getHeight();
				
				// Get the dimensions of the bitmap
				BitmapFactory.Options bmOptions = new BitmapFactory.Options();
				bmOptions.inJustDecodeBounds = true;
				
				InputStream inputStream = getContentResolver().openInputStream(
						uri);
				
				BitmapFactory.decodeStream(inputStream, null, bmOptions);
				inputStream.close();
				
				int photoW = bmOptions.outWidth;
				int photoH = bmOptions.outHeight;
				
				// Determine how much to scale down the image
				int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
				
				// Decode the image file into a Bitmap sized to fill the View
				bmOptions.inJustDecodeBounds = false;
				bmOptions.inSampleSize = scaleFactor;
				bmOptions.inPurgeable = true;
				
				inputStream = getContentResolver().openInputStream(uri);
				Bitmap bitmap = BitmapFactory.decodeStream(
						inputStream,
						null,
						bmOptions);
				if (bitmap == null) {
					Log.d(TAG, "bitmap is null ");
				} else {
					imageView.setImageBitmap(bitmap);
				}
				inputStream.close();
			}
			catch (IOException ex) {
				Log.e(TAG, "exception :" + ex);
			}
		}
	}
}