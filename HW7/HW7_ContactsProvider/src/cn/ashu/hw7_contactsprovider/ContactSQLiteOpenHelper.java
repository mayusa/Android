package cn.ashu.hw7_contactsprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactSQLiteOpenHelper extends SQLiteOpenHelper {
	// primary key, CursorAdapter will use this
	public static final String KEY_ID = "_id";

	// Create public field for each column in your table.
	private static final String DATABASE_NAME = "contacts.db";
	private static final String DATABASE_TABLE = "contacts";
	private static final int DATABASE_VERSION = 1;

	// The name of each column in your database.
	// These should be descriptive.
	public static final String CONTACT_NAME = "name";
	public static final String CONTACT_PHONE = "phone";
	public static final String CONTACT_EMAIL = "email";
	public static final String CONTACT_POSTAL = "postal";

	// The index (key) column name for use in where clauses.
	/*
	 * public static final int _ID_COLUMN = 0; public static final int
	 * NAME_COLUMN = 1; public static final int PHONE_COLUMN = 2; public static
	 * final int EMAIL_COLUMN = 3; public static final int POSTAL_COLUMN = 4;
	 */

	// SQL Statement to create a new database.
	private static final String DATABASE_CREATE = "create table "
			+ DATABASE_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement, " 
			+ CONTACT_NAME + " text, "
			+ CONTACT_PHONE + " text, " 
			+ CONTACT_EMAIL + " text, "
			+ CONTACT_POSTAL + " text);";

	// Variable to hold the database instance

	public ContactSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Called when no database exists in disk and the helper class needs
	// to create a new one.

	@Override
	public void onCreate(SQLiteDatabase _db) {
		_db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
		// Log the version upgrade.
		/*
		 * Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion +
		 * " to " + newVersion + ", which will destroy all old data");
		 */
		_db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
		onCreate(_db);
	}
}
