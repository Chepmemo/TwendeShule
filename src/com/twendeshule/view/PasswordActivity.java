package com.twendeshule.view;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.twendeshule.TwendeShule;
import com.twendeshule.R;
import com.twendeshule.controller.RegisterParentHTTPTask;

public class PasswordActivity extends Activity {
	
	private EditText password;
	private EditText passwordRepeat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_activity);
		
		password = (EditText)findViewById(R.id.set_password_text);
		passwordRepeat = (EditText)findViewById(R.id.repeat_password_text);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.password, menu);
		return true;
	}
	
 public void registerParent(View v){
    	
        SharedPreferences settings = getSharedPreferences(TwendeShule.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        
        editor.putInt("CURR_STEP", 1);
        
        editor.commit();
        
        Map<String, String> data = populate();
        
        if(password.getText().toString().equals(passwordRepeat.getText().toString())){
        	
        	data.put("PASSWORD", password.getText().toString());
            
    		new RegisterParentHTTPTask(this, data).execute(TwendeShule.SERVER_URL);
        	
        } else {
        	
        	Toast.makeText(this, "Password mismatch. Please enter similar passwords", Toast.LENGTH_LONG).show();
        	
        }
    	
    }
 
 	private Map<String, String> populate(){
		Map<String, String> data = new HashMap<String, String>();
		
		SharedPreferences settings = getSharedPreferences(TwendeShule.PREFS_NAME, Context.MODE_PRIVATE);
		
		data.put("FULL_NAME", settings.getString("FULL_NAME", "404"));
		data.put("EMAIL", settings.getString("EMAIL", "404"));
		data.put("RESIDENCE", settings.getString("RESIDENCE", "404"));
		data.put("PHONE", settings.getString("PHONE", "404"));
		
		return data;
		
	}

}
