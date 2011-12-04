package com.androidcourse.hw4.dao;

import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.androidcourse.hw4.util.Category;

public class TranslatorDAODBImpl extends SQLiteOpenHelper implements
		TranslatorDAO {

	private static final String DATABASE_NAME = "applicationTranslator";
	private static final int DATABASE_VERSION = 1;
	private static final String CATEGORY_TABLE_NAME = "categories";
	private SQLiteDatabase database;
	private final String CATEGORY_TABLE_CREATE = "create table "
			+ CATEGORY_TABLE_NAME
			+ " (_id integer primary key autoincrement, "
			+ "category text not null);";
	private static final String KEY_ROWID = "_id";
	public static final String KEY_CATEGORY = "category";

	public TranslatorDAODBImpl(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

	@Override
	public Cursor getCategoriesCursor() {
		if (database == null) {
			database = getWritableDatabase();
			insertCategories();
		}
		return database.query(CATEGORY_TABLE_NAME, new String[] {
				KEY_ROWID,
				KEY_CATEGORY }, null, null, null,
				null, null);
	}

	private void insertCategories() {
		database.insert(CATEGORY_TABLE_NAME, null,
				getCategoryContentValues(Category.MEDICAL));
		database.insert(CATEGORY_TABLE_NAME, null,
				getCategoryContentValues(Category.SOCIAL));
	}

	private ContentValues getCategoryContentValues(String category) {
		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORY, category);
		return values;
	}

}
