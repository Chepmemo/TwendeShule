package com.twendeshule.view;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.twendeshule.TwendeShule;
import com.twendeshule.R;
import com.twendeshule.controller.LoginHTTPTask;

public class LoginActivity extends Activity{
	
	private EditText phone;
	private EditText password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		phone = (EditText)findViewById(R.id.phone_text);
		password = (EditText)findViewById(R.id.password_text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void login(View v) {
		if(validate()){
			
			Map<String, String> data = new HashMap<String, String>();
			
			data.put("parent_phone", phone.getText().toString());
			data.put("parent_password", password.getText().toString());
			
			new LoginHTTPTask(this, data).execute(TwendeShule.SERVER_URL);
		
		} else {
			
			Toast.makeText(this, "You did not provide all your login credentials!", Toast.LENGTH_SHORT).show();
			
		}
	}
	public void register(View v){
		
		Intent intent = new Intent(LoginActivity.this, ParentActivity.class);
    	
		LoginActivity.this.startActivity(intent);
		
	}
	private boolean validate(){
		
		return (!phone.getText().toString().equals("") && !password.getText().toString().equals(""));
		
	}
}
