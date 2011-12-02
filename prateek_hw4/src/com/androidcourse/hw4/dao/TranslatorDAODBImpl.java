package com.androidcourse.hw4.dao;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class TranslatorDAODBImpl extends SQLiteOpenHelper implements
		TranslatorDAO {

	private final static String DATABASE_NAME = "applicationTranslator";
	private final static int DATABASE_VERSION = 1;
	private final SQLiteDatabase database;
	private final String CATEGORY_TABLE_CREATE = "create table translation_categories (_id integer primary key autoincrement, "
			+ "category text not null);";

	public TranslatorDAODBImpl(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		database = getWritableDatabase();
	}

	@Override
	public List<String> getCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> getTranslations(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> getDefaultTranslations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTranslations(Bundle bundle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CATEGORY_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		throw new RuntimeException(
				"DB:ApplicationTranslator upgrade not supported");

	}

}
