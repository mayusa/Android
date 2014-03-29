package cn.ashu.hw7_contactssqlite;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class ContactDBAdapter {
	// Context of the application using the database.
	private final Context context;
	// Database open/upgrade helper
	private ContactSQLiteOpenHelper dbHelper;
	private SQLiteDatabase db;
	private static final String DATABASE_TABLE = "contacts";
	// primary key, CursorAdapter will use this
	public static final String KEY_ID = "_id";
	// The name of each column in your database.
	// These should be descriptive.
	public static final String CONTACT_NAME = "name";
	public static final String CONTACT_PHONE = "phone";
	public static final String CONTACT_EMAIL = "email";
	public static final String CONTACT_POSTAL = "postal";

	public ContactDBAdapter(Context _context) {
		this.context = _context;
		// init for custom SQLiteOpenHelper too
		dbHelper = new ContactSQLiteOpenHelper(context);
	}

	// wrapper method, will let db helper to do underlying open
	public void open() throws SQLiteException {
		try {
			// on normal, open the database for read/writable
			db = dbHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			// on abnormal exception, open the database for read only
			db = dbHelper.getReadableDatabase();
		}
	}

	// wrapper method, release database object
	public void close() {
		db.close();
	}

	// Insert a new entry (consists a set of rows) into the table
	public long insertEntry(ContactInfo info) {
		// Create a new set of row values to insert.
		ContentValues rows = new ContentValues();
		// Assign column value for each row.
		rows.put(CONTACT_NAME, info.getName());
		rows.put(CONTACT_PHONE, info.getPhone());
		rows.put(CONTACT_EMAIL, info.getEmail());
		rows.put(CONTACT_POSTAL, info.getPostal());
		// Insert the rows.
		return db.insert(DATABASE_TABLE, null, rows);
	}

	// return a single DataModel object based on what name to search
	public ContactInfo getEntry(String searchname) throws SQLException {
		Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,
				CONTACT_NAME, CONTACT_PHONE, CONTACT_EMAIL, CONTACT_POSTAL },
				CONTACT_NAME + "=" + "'" + searchname.trim() + "'", null, null,
				null, null, null);
		if ((cursor.getCount() == 0) || !cursor.moveToFirst()) {
			throw new SQLException("No item found for name: " + searchname);
		}
		int theId = cursor.getInt(cursor.getColumnIndex(KEY_ID));
		String name = cursor.getString(cursor.getColumnIndex(CONTACT_NAME));
		String phone = cursor.getString(cursor.getColumnIndex(CONTACT_PHONE));
		String email = cursor.getString(cursor.getColumnIndex(CONTACT_EMAIL));
		String postaladdr = cursor.getString(cursor
				.getColumnIndex(CONTACT_POSTAL));
		ContactInfo result = new ContactInfo(theId, name, phone, email,
				postaladdr);
		return result;
	}

	public List<ContactInfo> getAllEntries() {
		List<ContactInfo> list = new ArrayList<ContactInfo>();
		Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,
				CONTACT_NAME, CONTACT_PHONE, CONTACT_EMAIL, CONTACT_POSTAL },
				null, null, null, null, null);
		while (cursor.moveToNext()) {
			int theId = cursor.getInt(cursor.getColumnIndex(KEY_ID));
			String name = cursor.getString(cursor.getColumnIndex(CONTACT_NAME));
			String phone = cursor.getString(cursor
					.getColumnIndex(CONTACT_PHONE));
			String email = cursor.getString(cursor
					.getColumnIndex(CONTACT_EMAIL));
			String postal = cursor.getString(cursor
					.getColumnIndex(CONTACT_POSTAL));
			ContactInfo info = new ContactInfo(theId, name, phone, email,
					postal);
			list.add(info);
		}
		return list;
	}

	// Remove all entries in the table
	public int deleteAllEntries() {
		int num = db.delete(DATABASE_TABLE, null, null);
		// int num = db.delete(DATABASE_TABLE, null, null);
		return num;
	}

	// Remove an entry based on its row index
	public int removeEntry(long _rowIndex) {
		int num = db.delete(DATABASE_TABLE, KEY_ID + "=?",
				new String[] { _rowIndex + "" });
		return num;
	}

}
