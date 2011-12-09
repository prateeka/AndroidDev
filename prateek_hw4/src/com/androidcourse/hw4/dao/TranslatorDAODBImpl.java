package com.androidcourse.hw4.dao;

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

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_CATEGORY_TYPE = "CATEGORY";
	public static final String COLUMN_TRANSLATION_LANG1 = "LANGUAGE_1_TRANSLATION";
	public static final String COLUMN_TRANSLATION_LANG2 = "LANGUAGE_2_TRANSLATION";
	public static final String COLUMN_CATEGORY_REF = "CATEGORY_ID";
	private final Context context;

	private SQLiteDatabase database = null;

	public TranslatorDAODBImpl(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public long addTranslations(Bundle bundle) {
		ContentValues translationsToInsert = createTranslationContentValues(bundle);

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
				COLUMN_ID,
				COLUMN_CATEGORY_TYPE }, null, null, null,
				null, null);
	}

	@Override
	public Cursor getTranslationCursor(long selectedCategoryItemID) {
		// System.out.println("selectedCategoryItemID is "
		// + String.valueOf(selectedCategoryItemID));
		return database.query(TRANSLATION_TABLE_NAME,
				new String[] {
						COLUMN_ID,
						COLUMN_TRANSLATION_LANG1,
						COLUMN_TRANSLATION_LANG2,
						COLUMN_CATEGORY_REF },
				COLUMN_CATEGORY_REF + "=" + selectedCategoryItemID,
				null, null, null, null);
	}

	protected void createCategoryTable() {
		/*-		final String CATEGORY_TABLE_DROP = "DROP TABLE IF EXISTS "
		 + CATEGORY_TABLE_NAME + ";";
		 database.execSQL(CATEGORY_TABLE_DROP);
		 */
		final String CATEGORY_TABLE_CREATE = "CREATE TABLE "
				+ " IF NOT EXISTS "
				+ CATEGORY_TABLE_NAME
				+ " ("
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_CATEGORY_TYPE + " TEXT NOT NULL"
				+ ") ;";
		database.execSQL(CATEGORY_TABLE_CREATE);
	}

	protected void createTranslationTable() {
		/*-		final String CATEGORY_TABLE_DROP = "DROP TABLE IF EXISTS "
		 + TRANSLATION_TABLE_NAME + ";";
		 database.execSQL(CATEGORY_TABLE_DROP);
		 */
		final String TRANSLATION_TABLE_CREATE = "CREATE TABLE "
				+ " IF NOT EXISTS "
				+ TRANSLATION_TABLE_NAME
				+ " ( "
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_TRANSLATION_LANG1 + " TEXT NOT NULL, "
				+ COLUMN_TRANSLATION_LANG2 + " TEXT NOT NULL, "
				+ COLUMN_CATEGORY_REF + " REFERENCES "
				+ CATEGORY_TABLE_NAME + "(" + COLUMN_ID + ")  NOT NULL"
				+ ") ;";
		database.execSQL(TRANSLATION_TABLE_CREATE);
	}

	private void insertCategories() {
		final String VAL_TO_REPLACE = " #VAL_TO_REPLACE ";
		final String CATEGORY_TABLE_INSERT = "INSERT INTO "
				+ CATEGORY_TABLE_NAME
				+ " ("
				+ COLUMN_CATEGORY_TYPE
				+ ") "
				+ " SELECT "
				+ "'" + VAL_TO_REPLACE + "'"
				+ " WHERE NOT EXISTS "
				+ " ( "
				+ "SELECT 1 FROM "
				+ CATEGORY_TABLE_NAME
				+ " WHERE " + COLUMN_CATEGORY_TYPE + "="
				+ "'" + VAL_TO_REPLACE + "'"
				+ ");";

		database.execSQL(CATEGORY_TABLE_INSERT.replaceAll(VAL_TO_REPLACE,
				Category.MEDICAL));
		database.execSQL(CATEGORY_TABLE_INSERT.replaceAll(VAL_TO_REPLACE,
				Category.SOCIAL));
	}

	private ContentValues createTranslationContentValues(Bundle bundle) {
		ContentValues translations = new ContentValues();
		translations.put(COLUMN_TRANSLATION_LANG1,
				(String) bundle.get(context.getResources()
						.getString(R.string.translationLang1)));
		translations.put(COLUMN_TRANSLATION_LANG2,
				(String) bundle.get(context.getResources()
						.getString(R.string.translationLang2)));
		translations.put(COLUMN_CATEGORY_REF,
				bundle.getLong(context.getResources().
						getString(R.string.categoryID)));
		return translations;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

}
