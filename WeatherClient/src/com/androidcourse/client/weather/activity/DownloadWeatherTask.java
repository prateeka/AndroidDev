package com.androidcourse.client.weather.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidcourse.client.weather.data.State;
import com.androidcourse.client.weather.data.WeatherDTO;
import com.androidcourse.client.weather.processor.WeatherDataManager;
import com.androidcourse.client.weather.processor.WeatherDays;

public class DownloadWeatherTask extends AsyncTask<String, Void, WeatherDTO> {
	private static final String TAG = "DownloadWeatherTask";
	
	private WeatherActivity weatherActivity;
	private WeatherDTO weatherDTO;
	private TextView conditionsText;
	private TextView celsiusTempText;
	private TextView farenTempText;
	private ImageView conditionsImage;
	private TextView dateText;
	
	final WeatherDays day;
	final Integer index;
	
	static final String DATE = "date";
	static final String CONDITIONS = "conditions";
	static final String CELSIUSTEMP = "celsiusTemp";
	static final String FARENTEMP = "farenTemp";
	static final String CONDITIONSIMAGE = "conditionImage";
	
	private final WeatherDataManager weatherDataManager;
	private static final Map<String, Integer> viewMap = new HashMap<String, Integer>();
	
	static {
		
		viewMap.put(DATE + "0", R.id.date0);
		viewMap.put(CONDITIONS + "0", R.id.conditions0);
		viewMap.put(CELSIUSTEMP + "0", R.id.celsiusTemp0);
		viewMap.put(FARENTEMP + "0", R.id.farenTemp0);
		viewMap.put(CONDITIONSIMAGE + "0", R.id.conditionImage0);
		
		viewMap.put(DATE + "1", R.id.date1);
		viewMap.put(CONDITIONS + "1", R.id.conditions1);
		viewMap.put(CELSIUSTEMP + "1", R.id.celsiusTemp1);
		viewMap.put(FARENTEMP + "1", R.id.farenTemp1);
		viewMap.put(CONDITIONSIMAGE + "1", R.id.conditionImage1);
		
		viewMap.put(DATE + "2", R.id.date2);
		viewMap.put(CONDITIONS + "2", R.id.conditions2);
		viewMap.put(CELSIUSTEMP + "2", R.id.celsiusTemp2);
		viewMap.put(FARENTEMP + "2", R.id.farenTemp2);
		viewMap.put(CONDITIONSIMAGE + "2", R.id.conditionImage2);
		
		viewMap.put(DATE + "3", R.id.date3);
		viewMap.put(CONDITIONS + "3", R.id.conditions3);
		viewMap.put(CELSIUSTEMP + "3", R.id.celsiusTemp3);
		viewMap.put(FARENTEMP + "3", R.id.farenTemp3);
		viewMap.put(CONDITIONSIMAGE + "3", R.id.conditionImage3);
		
		/*-		for (String key : viewMap.keySet()) {
		 Log.d(TAG, "key:value is " + key + ":" + viewMap.get(key));
		 }
		 */}
	
	DownloadWeatherTask(Context context,
			WeatherDays day, int index, WeatherDataManager weatherDataManager) {
		this.day = day;
		this.index = index;
		this.weatherDataManager = weatherDataManager;
		weatherActivity = (WeatherActivity) context;
		initViews(context);
	}
	
	// Called from main thread to re-attach
	void setContext(Context context) {
		initViews(context);
		if (getStatus() == AsyncTask.Status.FINISHED) {
			displayWeatherData();
		} else {
			displayDownloadingMsg();
		}
		weatherActivity = (WeatherActivity) context;
	}
	
	void initViews(Context context) {
		dateText = (TextView)
				((Activity) context).findViewById(viewMap.get(DATE
						+ index));
		
		conditionsText = (TextView)
				((Activity) context).findViewById(viewMap.get(CONDITIONS
						+ index));
		celsiusTempText = (TextView)
				((Activity) context).findViewById(viewMap.get(CELSIUSTEMP
						+ index));
		
		farenTempText = (TextView)
				((Activity) context).findViewById(viewMap.get(FARENTEMP
						+ index));
		conditionsImage = (ImageView)
				((Activity) context).findViewById(viewMap.get(CONDITIONSIMAGE
						+ index));
		
	}
	
	@Override
	protected void onPreExecute() {
		displayDownloadingMsg();
	}
	
	@Override
	protected WeatherDTO doInBackground(String... urls) {
		Log.d(TAG, "initiating downloading weather data for day  " + day);
		return downloadWeatherData();
	}
	
	@Override
	protected void onPostExecute(WeatherDTO result) {
		if (result != null) {
			weatherDTO = result;
			displayWeatherData();
			weatherActivity.checkAndEnableZipCodeView(index);
		}
		else {
			displayErrorMsg();
		}
	}
	
	private void displayErrorMsg() {
		final String ERROR_MSG = "Problem downloading weather data. Please try later";
		setTextMsg(conditionsText, ERROR_MSG);
		setTextMsg(celsiusTempText, ERROR_MSG);
		setTextMsg(farenTempText, ERROR_MSG);
	}
	
	private void displayDownloadingMsg() {
		final String DOWNLOADING_DATA = "Downloading";
		displayDate();
		setTextMsg(conditionsText, DOWNLOADING_DATA);
		setTextMsg(celsiusTempText, DOWNLOADING_DATA);
		setTextMsg(farenTempText, DOWNLOADING_DATA);
		setImage(conditionsImage, null);
	}
	
	private void displayDate() {
		setTextMsg(dateText, day.getDate());
	}
	
	void displayWeatherData() {
		if (weatherDTO != null) {
			setTextMsg(conditionsText, weatherDTO.getConditions());
			setTextMsg(celsiusTempText, weatherDTO.getCelsiusTemp());
			setTextMsg(farenTempText, weatherDTO.getFarenheitTemp());
			setImage(conditionsImage, weatherDTO.getBitmap());
		}
	}
	
	private void setImage(ImageView view, Bitmap bitmap) {
		if (bitmap == null) {
			view.setImageDrawable(null);
		} else {
			view.setImageBitmap(bitmap);
		}
	}
	
	private void setTextMsg(TextView view, String msg) {
		// Log.d(TAG, "view:msg is " + view + ":" + msg);
		view.setText(msg);
	}
	
	private WeatherDTO downloadWeatherData() {
		WeatherDTO weatherDTO = null;
		final int INVALID_SLEEP_INTERVAL = 1500; // millisec
		
		while (true) {
			if ((weatherDTO != null) && (weatherDTO.getState() == State.READY)) {
				Log.d(TAG, "weatherDTO for :" + day + " is downloaded as : "
						+ weatherDTO);
				break;
			} else if ((weatherDTO != null)
					&& (weatherDTO.getState() == State.INVALID)) {
				Log.d(TAG, "weatherDTO for :" + day + " is invalid as : "
						+ weatherDTO);
				break;
			}
			else {
				Log.v(TAG, "weatherDTO for :" + day
						+ " is still downloading...");
				weatherDTO = weatherDataManager.getWeather(day);
			}
			sleepFor(INVALID_SLEEP_INTERVAL);
		}
		return weatherDTO;
	}
	
	private void sleepFor(long msecs) {
		try {
			Thread.sleep(msecs);
		}
		catch (InterruptedException e) {
			Log.e(TAG, "sleep interrupted for day  " + day);
		}
	}
}
