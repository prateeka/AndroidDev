package arya.image.main;

import java.io.IOException;
import java.io.InputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

class ImageProcessor {
	private static final String TAG = "ImageClickListener";
	static final int LOAD_FROM_CAMERA = 1;
	static final int LOAD_FROM_GALLERY = 2;
	final HackImagePracticeActivity activity;
	
	ImageProcessor(HackImagePracticeActivity activity) {
		this.activity = activity;
		
	}
	
	void initiatePictureLoad(int loadDevice) {
		if (loadDevice == LOAD_FROM_GALLERY) {
			initiateImageLoadFromGallery();
		} else if (loadDevice == LOAD_FROM_CAMERA) {
			initiateImageLoadFromCamera();
		}
	}
	
	private void initiateImageLoadFromGallery() {
		Intent imageChooser = new Intent(Intent.ACTION_GET_CONTENT);
		imageChooser.setType("image/*");
		activity.startActivityForResult(imageChooser, LOAD_FROM_GALLERY);
	}
	
	private void initiateImageLoadFromCamera() {
	}
	
	boolean flipImage() {
		activity.getImageView().setRotation(
				activity.getImageView().getRotation() + 90);
		return true;
	}
	
	Uri loadPicture(int loadDevice, Intent data) {
		Uri uri = null;
		if (loadDevice == LOAD_FROM_GALLERY) {
			uri = data.getData();
		} else if (loadDevice == LOAD_FROM_CAMERA) {
		}
		
		if (uri == null) {
			Log.d(TAG, "data.getData() is null");
		} else {
			setPicFromUri(uri);
		}
		
		return uri;
	}
	
	private void setPicFromUri(Uri uri) {
		activity.getImageView().setRotation(0);
		
		try {
			// Get the dimensions of the View
			int targetW = activity.getImageView().getWidth();
			int targetH = activity.getImageView().getHeight();
			
			// Get the dimensions of the bitmap
			BitmapFactory.Options bmOptions = new BitmapFactory.Options();
			bmOptions.inJustDecodeBounds = true;
			
			InputStream inputStream = activity
					.getContentResolver()
					.openInputStream(uri);
			
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
			
			inputStream = activity.getContentResolver().openInputStream(uri);
			Bitmap bitmap = BitmapFactory.decodeStream(
					inputStream,
					null,
					bmOptions);
			if (bitmap == null) {
				Log.d(TAG, "bitmap is null ");
			} else {
				activity.getImageView().setImageBitmap(bitmap);
			}
			inputStream.close();
		}
		catch (IOException ex) {
			Log.e(TAG, "exception :" + ex);
		}
	}
}