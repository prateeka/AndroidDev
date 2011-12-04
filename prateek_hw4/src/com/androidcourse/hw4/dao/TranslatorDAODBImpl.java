package com.androidcourse.hw4.dao;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.androidcourse.hw4.util.Category;

public class TranslatorDAODBImpl extends SQLiteOpenHelper implements
		TranslatorDAO {

	private static final String DATABASE_NAME = "APPLICATION_TRANSLATOR";
	private static final int DATABASE_VERSION = 1;

	private static final String CATEGORY_TABLE_NAME = "CATEGORIES";
	private static final String TRANSLATION_TABLE_NAME = "TRANSLATIONS";

	public static final String KEY_CATEGORY_CATEGORY = "CATEGORY";
	private static final String KEY_CATEGORY_ROWID = "_id";
	private static final String KEY_CATEGORY_LANG1 = "LANGUAGE_1_TRANSLATION";
	private static final String KEY_CATEGORY_LANG2 = "LANGUAGE_2_TRANSLATION";

	private SQLiteDatabase database = null;

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
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		database = db;
		createCategoryTable();
		insertCategories();
		createTranslationTable();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		throw new RuntimeException(
				"DB:ApplicationTranslator upgrade not supported");
	}

	@Override
	public Cursor getCategoryCursor() {
		if (database == null) {
			getWritableDatabase();
		}
		return database.query(CATEGORY_TABLE_NAME, new String[] {
				KEY_CATEGORY_ROWID,
				KEY_CATEGORY_CATEGORY }, null, null, null,
				null, null);
	}

	@Override
	public Cursor getTranslationCursor() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void createCategoryTable() {
		/*-		final String CATEGORY_TABLE_DROP = "DROP table "
		 + CATEGORY_TABLE_NAME + ";";
		 database.execSQL(CATEGORY_TABLE_DROP);
		 */
		final String CATEGORY_TABLE_CREATE = "CREATE TABLE "
				+ " IF NOT EXISTS "
				+ CATEGORY_TABLE_NAME
				+ " ("
				+ KEY_CATEGORY_ROWID + " integer primary key autoincrement, "
				+ KEY_CATEGORY_CATEGORY + " text not null"
				+ ") ;";
		database.execSQL(CATEGORY_TABLE_CREATE);
	}

	private void createTranslationTable() {
		final String TRANSLATION_TABLE_CREATE = "create table "
				+ " if not exists "
				+ TRANSLATION_TABLE_NAME
				+ " ( "
				+ KEY_CATEGORY_ROWID + " integer primary key autoincrement, "
				+ KEY_CATEGORY_LANG1 + " text not null, "
				+ KEY_CATEGORY_LANG2 + " text not null "
				+ ") ;";
		database.execSQL(TRANSLATION_TABLE_CREATE);
	}

	private void insertCategories() {
		final String VAL_TO_REPLACE = " #VAL_TO_REPLACE ";
		final String CATEGORY_TABLE_INSERT = "INSERT INTO "
				+ CATEGORY_TABLE_NAME
				+ " ("
				+ KEY_CATEGORY_CATEGORY
				+ ") "
				+ " SELECT "
				+ "'" + VAL_TO_REPLACE + "'"
				+ " WHERE NOT EXISTS "
				+ " ( "
				+ "SELECT 1 FROM "
				+ CATEGORY_TABLE_NAME
				+ " WHERE " + KEY_CATEGORY_CATEGORY + "="
				+ "'" + VAL_TO_REPLACE + "'"
				+ ");";

		database.execSQL(CATEGORY_TABLE_INSERT.replaceAll(VAL_TO_REPLACE,
				Category.MEDICAL));
		database.execSQL(CATEGORY_TABLE_INSERT.replaceAll(VAL_TO_REPLACE,
				Category.SOCIAL));
	}

	/*-private ContentValues getCategoryContentValues(String category) {
		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORY_CATEGORY, category);
		return values;
	}*/

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

}
