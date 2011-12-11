package com.androidcourse.grocery.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.androidcourse.grocery.util.GroceryConstants;

public class GroceryDAODBImpl extends SQLiteOpenHelper implements
		GroceryDAO {

	private static final String DATABASE_NAME = "groceryApp.db";
	private static final int DATABASE_VERSION = 1;

	private static final String TABLE_TRADER = "TRADER_INFO";
	private static final String TABLE_ITEM = "ITEM";

	public static final String COLUMN_ID = "_id";
	public static final String TABLE_TRADER_COLUMN_TRADER_NAME = "NAME";
	public static final String TABLE_ITEM_COLUMN_ITEM_NAME = "NAME";
	public static final String TABLE_ITEM_COLUMN_ITEM_QTY = "QUANTITY";
	public static final String TABLE_ITEM_COLUMN_ITEM_NOTE = "NOTE";
	public static final String TABLE_ITEM_COLUMN_TRADER_REF = "TRADER_ID";

	private SQLiteDatabase database = null;

	public GroceryDAODBImpl(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public long addItem(Bundle bundle) {
		ContentValues itemToAdd = createItemContentValues(bundle);

		return database.insert(TABLE_ITEM, null,
				itemToAdd);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		database = db;
		createTraderTable();
		insertTraders();
		createItemTable();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		throw new RuntimeException(
				"DB:GroceryApp upgrade not supported");
	}

	@Override
	public Cursor getTraderCursor() {
		if (database == null) {
			getWritableDatabase();
		}
		return database.query(TABLE_TRADER, new String[] {
				COLUMN_ID,
				TABLE_TRADER_COLUMN_TRADER_NAME }, null, null, null,
				null, null);
	}

	@Override
	public Cursor getItemCursor(long traderId) {
		// System.out.println("selectedTraderID is "
		// + String.valueOf(selectedTraderID));
		return database.query(TABLE_ITEM,
				new String[] {
						COLUMN_ID,
						TABLE_ITEM_COLUMN_ITEM_NAME,
						TABLE_ITEM_COLUMN_ITEM_QTY,
						TABLE_ITEM_COLUMN_TRADER_REF },
				TABLE_ITEM_COLUMN_TRADER_REF + "=" + traderId,
				null, null, null, null);
	}

	protected void createTraderTable() {
		/*-		final String TRADER_TABLE_DROP_STMT = "DROP TABLE IF EXISTS "
		 + TRADER_TABLE_CREATE_STMT + ";";
		 database.execSQL(TRADER_TABLE_DROP_STMT);
		 */
		final String TRADER_TABLE_CREATE_STMT = "CREATE TABLE "
				+ " IF NOT EXISTS "
				+ TABLE_TRADER
				+ " ("
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ TABLE_TRADER_COLUMN_TRADER_NAME + " TEXT NOT NULL"
				+ ") ;";
		database.execSQL(TRADER_TABLE_CREATE_STMT);
	}

	protected void createItemTable() {
		/*-		final String ITEM_TABLE_DROP_STMT = "DROP TABLE IF EXISTS "
		 + TABLE_ITEM + ";";
		 database.execSQL(ITEM_TABLE_DROP_STMT);
		 */
		final String ITEM_TABLE_CREATE_STMT = "CREATE TABLE "
				+ " IF NOT EXISTS "
				+ TABLE_ITEM
				+ " ( "
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ TABLE_ITEM_COLUMN_ITEM_NAME + " TEXT NOT NULL, "
				+ TABLE_ITEM_COLUMN_ITEM_QTY + " INTEGER, "
				+ TABLE_ITEM_COLUMN_TRADER_REF + " REFERENCES "
				+ TABLE_TRADER + "(" + COLUMN_ID + ")  "
				+ ") ;";
		database.execSQL(ITEM_TABLE_CREATE_STMT);
	}

	private void insertTraders() {
		final String VAL_TO_REPLACE = " #VAL_TO_REPLACE ";
		final String ITEM_INSERT_STMT = "INSERT INTO "
				+ TABLE_TRADER
				+ " ("
				+ TABLE_TRADER_COLUMN_TRADER_NAME
				+ ") "
				+ " SELECT "
				+ "'" + VAL_TO_REPLACE + "'"
				+ " WHERE NOT EXISTS "
				+ " ( "
				+ "SELECT 1 FROM "
				+ TABLE_TRADER
				+ " WHERE " + TABLE_TRADER_COLUMN_TRADER_NAME + "="
				+ "'" + VAL_TO_REPLACE + "'"
				+ ");";

		database.execSQL(ITEM_INSERT_STMT.replaceAll(VAL_TO_REPLACE,
				GroceryConstants.TRADER_FREDMYER));
		database.execSQL(ITEM_INSERT_STMT.replaceAll(VAL_TO_REPLACE,
				GroceryConstants.TRADER_COSTCO));
	}

	private ContentValues createItemContentValues(Bundle bundle) {
		ContentValues itemContentValues = new ContentValues();
		itemContentValues.put(TABLE_ITEM_COLUMN_ITEM_NAME,
				(String) bundle.get(GroceryConstants.ITEM_NAME));
		itemContentValues.put(TABLE_ITEM_COLUMN_ITEM_QTY,
				bundle.getFloat(GroceryConstants.ITEM_QTY));
		itemContentValues.put(TABLE_ITEM_COLUMN_TRADER_REF,
				bundle.getLong(GroceryConstants.TRADER_ID));
		return itemContentValues;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

}
