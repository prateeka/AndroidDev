package com.arya.androidcourse.service.http;

import android.os.Parcel;
import android.os.Parcelable;

public class ParseableByteArray implements Parcelable
{
	private byte[] byteArray = null;
	public static final Parcelable.Creator<ParseableByteArray> CREATOR =
			new Parcelable.Creator<ParseableByteArray>()
			{
				@Override
				public ParseableByteArray createFromParcel(Parcel in) {
					return new ParseableByteArray(in);
				}
				
				@Override
				public ParseableByteArray[] newArray(int size) {
					return new ParseableByteArray[size];
				}
			};
	
	public ParseableByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}
	
	private ParseableByteArray(Parcel in) {
		readFromParcel(in);
	}
	
	private void readFromParcel(Parcel in) {
		in.readByteArray(byteArray);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeByteArray(byteArray);
	}
}
