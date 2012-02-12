package com.arya.androidcourse.service.http;

import android.os.Parcel;
import android.os.Parcelable;

public class ParseableByteArray implements Parcelable
{
	private int byteArrayLength;
	private byte[] byteArray;
	
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
		byteArrayLength = in.readInt();
		byteArray = new byte[byteArrayLength];
		in.readByteArray(byteArray);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(byteArray.length);
		out.writeByteArray(byteArray);
	}
	
	public byte[] getByteArray() {
		return byteArray;
	}
	
	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}
}
