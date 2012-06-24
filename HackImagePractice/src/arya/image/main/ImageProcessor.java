package arya.image.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

class ImageProcessor {
	private static final String TAG = "ImageClickListener";
	static final int LOAD_FROM_CAMERA = 1;
	static final int LOAD_FROM_GALLERY = 2;
	private Uri cameraFileSaveUri = null;
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
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// create a file to save the image
		cameraFileSaveUri = getOutputMediaFileUri();
		// set the image file name
		intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraFileSaveUri);
		// start the image capture Intent
		activity.startActivityForResult(
				intent, LOAD_FROM_CAMERA);
	}
	
	private Uri getOutputMediaFileUri() {
		final String IMAGE_FILE_DIR = "SnapQi";
		final String IMAGE_FILE_NAME = "pic.jpg";
		
		File mediaStorageDir = new File(
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_FILE_DIR);
		
		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.e(TAG, "failed to create directory");
				return null;
			}
		}
		
		// Create a media file name
		File mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ IMAGE_FILE_NAME);
		Uri uri = Uri.fromFile(mediaFile);
		return uri;
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
			uri = cameraFileSaveUri;
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