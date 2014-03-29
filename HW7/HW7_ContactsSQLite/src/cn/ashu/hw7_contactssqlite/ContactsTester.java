package cn.ashu.hw7_contactssqlite;

import java.util.List;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactsTester extends Activity {

	ContactDBAdapter contactDBAdapter;
	private ListView lv_contacts;
	private List<ContactInfo> contactInfos;
	private ContactAdapter ca;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_tester);
		lv_contacts = (ListView) findViewById(R.id.lv1);
		contactDBAdapter = new ContactDBAdapter(this);
		// open or create the DB
		contactDBAdapter.open();
		ca = new ContactAdapter();
		contactInfos = contactDBAdapter.getAllEntries();
	}

	public void goAndcontact(View view) {
		Intent intent = new Intent(this, ContactsFormActivity.class);
		startActivityForResult(intent, 1);
	}

	// 当新开启的activity关闭时调用的方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// System.out.println("onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			String name = data.getStringExtra("name");
			if (requestCode == 1) {
				ca.notifyDataSetChanged();
				Toast.makeText(this, name + " added!", Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	// get content resolver
	public void showProvider(View view) {
		contactInfos.clear();
		ContentResolver resolver = getContentResolver();
		Uri uri = Uri
				.parse("content://cn.ashu.contactcontentprovider/contacts");
		Cursor cursor = resolver.query(uri, null, null, null, null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String phone = cursor.getString(cursor.getColumnIndex("phone"));
			String email = cursor.getString(cursor.getColumnIndex("email"));
			String postal = cursor.getString(cursor.getColumnIndex("postal"));
			ContactInfo info = new ContactInfo(name, phone, email, postal);
			contactInfos.add(info);
		}
		lv_contacts.setAdapter(ca);
		Toast.makeText(this, "The contacts from Content Provider!",
				Toast.LENGTH_LONG).show();
	}

	public void showAll(View view) {
		if (contactInfos.isEmpty()) {
			Toast.makeText(this, "The DB is empty!", Toast.LENGTH_LONG).show();
		} else {
			contactInfos.clear();
			contactInfos = contactDBAdapter.getAllEntries();
			lv_contacts.setAdapter(ca);
		}
	}

	public void deleteAll(View view) {
		if (contactDBAdapter.deleteAllEntries() != 0) {
			ca.notifyDataSetChanged();
			Toast.makeText(this, "All contacts are deleted!", Toast.LENGTH_LONG)
					.show();
		} else {
			Toast.makeText(this, "The DB is empty!", Toast.LENGTH_LONG).show();
		}
	}

	private class ContactAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return contactInfos.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ContactInfo info = contactInfos.get(position);
			// 注意布局文件要选 contact_item !!!
			View view = View.inflate(getApplicationContext(),
					R.layout.contact_item, null);
			TextView tv_name = (TextView) view
					.findViewById(R.id.tv_contact_name);
			TextView tv_number = (TextView) view
					.findViewById(R.id.tv_contact_number);
			tv_name.setText(info.getName());
			tv_number.setText(info.getPhone());
			return view;
		}

		@Override
		public void notifyDataSetChanged() {
			contactInfos.clear();
			contactInfos = contactDBAdapter.getAllEntries();
			lv_contacts.setAdapter(ca);
		}
	}

	public void onDestroy() {
		// close the DB
		super.onDestroy();
		contactDBAdapter.close();
	}
}
