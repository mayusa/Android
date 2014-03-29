package cn.ashu.hw7_contactsprovider;

import cn.ashu.hw7_contactsprovider.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ContactsFormActivity extends Activity {
	//TabHost mTabHost = null;
	EditText et_name;
	EditText et_phone;
	EditText et_email;
	EditText et_postal;
	 ContactDBAdapter contactDBAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		contactDBAdapter = new ContactDBAdapter(this);
		// open or create the DB
		contactDBAdapter.open();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts);
		
		et_name = (EditText) findViewById(R.id.et_name);
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_email = (EditText) findViewById(R.id.et_email);
		et_postal = (EditText) findViewById(R.id.et_postal);		
	}

	public void clickSave(View view) {
		String name = et_name.getText().toString();
		String phone = et_phone.getText().toString();
		String email = et_email.getText().toString();
		String postal = et_postal.getText().toString();
		
		// System.out.println("save"+ name+phone+email+postal);
		if (TextUtils.isEmpty(name)) {
			Toast.makeText(this, "The name is empty!!", Toast.LENGTH_SHORT).show();
		} else {
			ContactInfo info = new ContactInfo(name, phone, email, postal);
			if(contactDBAdapter.insertEntry(info)!=0){
				Intent data = new Intent();
				data.putExtra("name", name);
				setResult(0, data);
			}
			finish();
		}
	}
	public void onDestroy() {
		// close the DB
		super.onDestroy();
		contactDBAdapter.close();
	  }

}
