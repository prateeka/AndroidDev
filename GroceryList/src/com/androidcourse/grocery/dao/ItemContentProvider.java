package com.androidcourse.grocery.dao;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.androidcourse.grocery.factory.GroceryFactory;

public class ItemContentProvider extends ContentProvider {
	
	private static final String TAG = "ItemContentProvider";
	
	// database
	private GroceryDAO groceryDAO;
	
	// Used for the UriMacher
	private static final int INCOMING_ITEMS_COLLECTION_URI_INDICATOR = 1;
	private static final int INCOMING_SINGLE_ITEM_URI_INDICATOR = 2;
	
	private static final String AUTHORITY = "com.androidcourse.grocery.contentprovider.item";
	private static final String BASE_PATH = "items";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH);
	
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/items";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/item";
	
	private static final UriMatcher sURIMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(
				AUTHORITY,
				BASE_PATH,
				INCOMING_ITEMS_COLLECTION_URI_INDICATOR);
		sURIMatcher.addURI(
				AUTHORITY,
				BASE_PATH + "/#",
				INCOMING_SINGLE_ITEM_URI_INDICATOR);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		throw new RuntimeException("Operation not supported");
	}
	
	@Override
	public String getType(Uri uri) {
		throw new RuntimeException("Operation not supported");
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		throw new RuntimeException("Operation not supported");
	}
	
	@Override
	public boolean onCreate() {
		GroceryFactory factory = GroceryFactory.getFactory(getContext());
		groceryDAO = factory.getGroceryDAO();
		return true;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		int uriType = sURIMatcher.match(uri);
		Cursor cursor;
		switch (uriType) {
			case INCOMING_ITEMS_COLLECTION_URI_INDICATOR:
				Log.d(
						TAG,
						"querying for items collection for selection criteria "
								+ selection);
				cursor = groceryDAO.getItemCursor(selection, selectionArgs);
				break;
			case INCOMING_SINGLE_ITEM_URI_INDICATOR:
				selection = GroceryDAODBImpl.KEY_COLUMN_ID + "="
						+ uri.getLastPathSegment();
				
				Log.d(
						TAG,
						"querying for single items with id "
								+ selection);
				cursor = groceryDAO.getItemCursor(selection, selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		return cursor;
	}
	
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		throw new RuntimeException("Operation not supported");
	}
	
}
