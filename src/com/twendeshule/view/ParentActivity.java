package com.twendeshule.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.twendeshule.TwendeShule;
import com.twendeshule.R;

public class ParentActivity extends Activity {
	
	private EditText fullNames;
	private EditText email;
	private EditText residence;
	private EditText phone;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_activity);
        
        fullNames = (EditText)findViewById(R.id.parent_fullnames_text);
		email = (EditText)findViewById(R.id.parent_email_text);
		residence = (EditText)findViewById(R.id.parent_residence_text);
		phone = (EditText)findViewById(R.id.parent_phone_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.parent, menu);
        return true;
    }
    
    public void registerParent(View v){
    	
        SharedPreferences settings = getSharedPreferences(TwendeShule.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        
        editor.putInt("CURR_STEP", 1);
        
        editor.putString("FULL_NAME", fullNames.getText().toString());
        editor.putString("EMAIL", email.getText().toString());
        editor.putString("RESIDENCE", residence.getText().toString());
        editor.putString("PHONE", phone.getText().toString());
        
        editor.commit();
        
        Intent intent = new Intent(ParentActivity.this, PasswordActivity.class);
		
        ParentActivity.this.startActivity(intent);
    	
    }
}
