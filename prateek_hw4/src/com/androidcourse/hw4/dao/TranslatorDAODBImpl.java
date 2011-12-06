package com.androidcourse.hw4.dao;

import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.androidcourse.hw4.R;
import com.androidcourse.hw4.util.Category;

public class TranslatorDAODBImpl extends SQLiteOpenHelper implements
		TranslatorDAO {

	private static final String DATABASE_NAME = "APPLICATION_TRANSLATOR";
	private static final int DATABASE_VERSION = 1;

	private static final String CATEGORY_TABLE_NAME = "CATEGORIES";
	private static final String TRANSLATION_TABLE_NAME = "TRANSLATIONS";

	public static final String KEY_ID = "_id";
	public static final String KEY_CATEGORY_TYPE = "CATEGORY";
	public static final String KEY_TRANSLATION_LANG1 = "LANGUAGE_1_TRANSLATION";
	public static final String KEY_TRANSLATION_LANG2 = "LANGUAGE_2_TRANSLATION";
	public static final String KEY_CATEGORY_REF = "CATEGORY_ID";
	private final Context context;

	private SQLiteDatabase database = null;

	public TranslatorDAODBImpl(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
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
	public long addTranslations(Bundle bundle) {
		ContentValues translationsToInsert = createTranslationContentValues(
				(String) bundle.get(context.getResources()
						.getString(R.string.translationKeyLang1)),
				(String) bundle.get(context.getResources()
						.getString(R.string.translationKeyLang2))
				);

		return database.insert(TRANSLATION_TABLE_NAME, null,
				translationsToInsert);
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
				KEY_ID,
				KEY_CATEGORY_TYPE }, null, null, null,
				null, null);
	}

	@Override
	public Cursor getTranslationCursor() {
		return database.query(TRANSLATION_TABLE_NAME, new String[] {
				KEY_ID,
				KEY_TRANSLATION_LANG1,
				KEY_TRANSLATION_LANG2,
				KEY_CATEGORY_REF }, null, null, null,
				null, null);
	}

	protected void createCategoryTable() {
		final String CATEGORY_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ CATEGORY_TABLE_NAME + ";";
		database.execSQL(CATEGORY_TABLE_DROP);

		final String CATEGORY_TABLE_CREATE = "CREATE TABLE "
				+ " IF NOT EXISTS "
				+ CATEGORY_TABLE_NAME
				+ " ("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ KEY_CATEGORY_TYPE + " TEXT NOT NULL"
				+ ") ;";
		database.execSQL(CATEGORY_TABLE_CREATE);
	}

	protected void createTranslationTable() {
		final String CATEGORY_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ TRANSLATION_TABLE_NAME + ";";
		database.execSQL(CATEGORY_TABLE_DROP);

		final String TRANSLATION_TABLE_CREATE = "CREATE TABLE "
				+ " IF NOT EXISTS "
				+ TRANSLATION_TABLE_NAME
				+ " ( "
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ KEY_TRANSLATION_LANG1 + " TEXT NOT NULL, "
				+ KEY_TRANSLATION_LANG2 + " TEXT NOT NULL, "
				+ KEY_CATEGORY_REF + " REFERENCES "
				+ CATEGORY_TABLE_NAME + "(" + KEY_ID + ")  NOT NULL"
				+ ") ;";
		database.execSQL(TRANSLATION_TABLE_CREATE);
	}

	private void insertCategories() {
		final String VAL_TO_REPLACE = " #VAL_TO_REPLACE ";
		final String CATEGORY_TABLE_INSERT = "INSERT INTO "
				+ CATEGORY_TABLE_NAME
				+ " ("
				+ KEY_CATEGORY_TYPE
				+ ") "
				+ " SELECT "
				+ "'" + VAL_TO_REPLACE + "'"
				+ " WHERE NOT EXISTS "
				+ " ( "
				+ "SELECT 1 FROM "
				+ CATEGORY_TABLE_NAME
				+ " WHERE " + KEY_CATEGORY_TYPE + "="
				+ "'" + VAL_TO_REPLACE + "'"
				+ ");";

		database.execSQL(CATEGORY_TABLE_INSERT.replaceAll(VAL_TO_REPLACE,
				Category.MEDICAL));
		database.execSQL(CATEGORY_TABLE_INSERT.replaceAll(VAL_TO_REPLACE,
				Category.SOCIAL));
	}

	private ContentValues createTranslationContentValues(String lang1,
			String lang2) {
		ContentValues translations = new ContentValues();
		translations.put(KEY_TRANSLATION_LANG1, lang1);
		translations.put(KEY_TRANSLATION_LANG2, lang2);
		return translations;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

}
