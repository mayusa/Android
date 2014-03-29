package cn.ashu.hw7_contactsprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class ContactContentProvider extends ContentProvider {
	// private static final String TAG = "ContactContentProvider";
	private ContactSQLiteOpenHelper helper;
	private SQLiteDatabase db;
	private static final String DATABASE_TABLE = "contacts";
	public static final String AUTHORITY = "cn.ashu.contactcontentprovider";
	//
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/contacts");

	private static final int ALLROWS = 1;
	private static final int SINGLE_ROW = 2;
	// 匹配器
	private static final UriMatcher uriMatcher;
	// Populate the UriMatcher object, where a URI ending in 'todoitems' will
	// correspond to a request for all items, and 'todoitems/[rowID]'
	// represents a single row. 匹配规则
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher
				.addURI("cn.ashu.contactcontentprovider", "contacts", ALLROWS);
		uriMatcher.addURI("cn.ashu.contactcontentprovider", "contacts/#",
				SINGLE_ROW);
	}

	@Override
	public boolean onCreate() {
		helper = new ContactSQLiteOpenHelper(getContext());
		db = helper.getWritableDatabase();
		return (db == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		switch (uriMatcher.match(uri)) {
		case ALLROWS:
			Cursor cursor = db.query(DATABASE_TABLE, projection, selection,
					selectionArgs, null, null, sortOrder);
			return cursor;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// Return a string that identifies the MIME type
		// for a Content Provider URI
		switch (uriMatcher.match(uri)) {
		case ALLROWS:
			return "vnd.android.cursor.dir/vnd.ashu.contacts";
		case SINGLE_ROW:
			return "vnd.android.cursor.item/vnd.ashu.contacts";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}
}
